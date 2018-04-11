package oldstyle;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Lesson01 {
    private static Logger logger = LoggerFactory.getLogger(Lesson01.class);

    public static void main(String[] args) throws IOException, XMLStreamException {

        String inFileName = ((args.length == 0) || args[0].isEmpty())?"data/RU-NVS.osm.bz2":args[0];

        File inFile = new File(inFileName);

        if(!Files.exists(inFile.toPath())){
            throw new IOException(String.format("File not found: %s", inFileName));
        }

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inFile))) {
            // подготовка к чтению файла
            InputStream iStream = new BZip2CompressorInputStream(bis);

            XMLInputFactory factory = XMLInputFactory.newInstance();

            XMLStreamReader r = factory.createXMLStreamReader(iStream);

            // создание списка для хранения всех встреченных атрибутов вида user элементов типа node
            List<String> users = new ArrayList<>();
            // создание hashmap для хранения всех видов встреченных атрибутов элементов типа node
            Map<String, Integer> keys = new HashMap<>();

            logger.info("Users read started");

            while (r.hasNext()) {
                try {
                    r.next();
                    // если элемент стартовый и его имя node
                    if (r.isStartElement() && r.getLocalName().equalsIgnoreCase("node")) {
                        // берем значение атрибута вида user
                        String username = r.getAttributeValue("", "user");

                        // добавляем юзера в лист (да если он уже там есть
                        if(username != null && !username.isEmpty())
                            users.add(username);

                        // бежим по всем абтрибутам элемента и сохраняем их имена в hashmap. если такой атрибут уже сохранён - инкрементируем его
                        for(int i = 0; i < r.getAttributeCount() ; i++){
                            if(keys.containsKey(r.getAttributeLocalName(i))){
                                keys.put(r.getAttributeLocalName(i), keys.get(r.getAttributeLocalName(i)) + 1);
                            }else{
                                keys.put(r.getAttributeLocalName(i), 1);
                            }
                        }
                    }
                } catch (XMLStreamException e1) {
                    e1.printStackTrace();
                }
            }
            // закрываем ридер
            r.close();

            logger.info("Users read complete");

            // группируем список пользователей в мапу с количеством пользователей и сохраняем в виде списка вида (user-count)
            List<Map.Entry<String, Long>> userList = new ArrayList<>(
                    users.stream()
                            .collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                            .entrySet());

            // сортируем список по полю count
            userList.sort((Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) -> {
                if(o2.getValue().equals(o1.getValue())){
                    return o1.getKey().compareTo(o2.getKey());
                }else {
                    return o2.getValue().compareTo(o1.getValue());
                }
            });

            // сохраняем пользователей
            File usersOutputFile = new File("users.txt");

            Files.deleteIfExists(usersOutputFile.toPath());

            try (BufferedWriter wr = new BufferedWriter(new FileWriter(usersOutputFile))) {
                for (Map.Entry<String, Long> entry : userList) {
                    wr.write(String.format("User: %s, count: %d", entry.getKey(), entry.getValue()));
                    wr.newLine();
                }
                wr.flush();
                wr.close();
            }

            // сохраняем ключи
            File keysOutputFile = new File("keys.txt");

            Files.deleteIfExists(keysOutputFile.toPath());

            try (BufferedWriter wr = new BufferedWriter(new FileWriter(keysOutputFile))) {
                for (Map.Entry<String, Integer> entry : keys.entrySet()) {
                    wr.write(String.format("Key: %s, count: %d", entry.getKey(), entry.getValue()));
                    wr.newLine();
                }
                wr.flush();
                wr.close();
            }
        }
    }
}
