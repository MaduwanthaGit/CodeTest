package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.Band;
import org.example.dto.Festival;

import java.util.*;

public class Procesor {

    //Sample Data set

    private final String json = "[\n" +
            "  {\n" +
            "    \"name\": \"LOL-palooza\",\n" +
            "    \"bands\": [\n" +
            "      {\n" +
            "        \"name\": \"Winter Primates\",\n" +
            "        \"recordLabel\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"Frank Jupiter\",\n" +
            "        \"recordLabel\": \"Pacific Records\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"Jill Black\",\n" +
            "        \"recordLabel\": \"Fourth Woman Records\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"Werewolf Weekday\",\n" +
            "        \"recordLabel\": \"XS Recordings\"\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Small Night In\",\n" +
            "    \"bands\": [\n" +
            "      {\n" +
            "        \"name\": \"Squint-281\",\n" +
            "        \"recordLabel\": \"Outerscope\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"The Black Dashes\",\n" +
            "        \"recordLabel\": \"Fourth Woman Records\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"Green Mild Cold Capsicum\",\n" +
            "        \"recordLabel\": \"Marner Sis. Recording\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"Yanke East\",\n" +
            "        \"recordLabel\": \"MEDIOCRE Music\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"Wild Antelope\",\n" +
            "        \"recordLabel\": \"Marner Sis. Recording\"\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Trainerella\",\n" +
            "    \"bands\": [\n" +
            "      {\n" +
            "        \"name\": \"Wild Antelope\",\n" +
            "        \"recordLabel\": \"Still Bottom Records\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"Summon\",\n" +
            "        \"recordLabel\": \"Outerscope\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"Manish Ditch\",\n" +
            "        \"recordLabel\": \"ACR\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"Adrian Venti\",\n" +
            "        \"recordLabel\": \"Monocracy Records\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"YOUKRANE\",\n" +
            "        \"recordLabel\": \"Anti Records\"\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Twisted Tour\",\n" +
            "    \"bands\": [\n" +
            "      {\n" +
            "        \"name\": \"Summon\",\n" +
            "        \"recordLabel\": \"Outerscope\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"Auditones\",\n" +
            "        \"recordLabel\": \"Marner Sis. Recording\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"Squint-281\"\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"bands\": [\n" +
            "      {\n" +
            "        \"name\": \"Critter Girls\",\n" +
            "        \"recordLabel\": \"ACR\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"Propeller\",\n" +
            "        \"recordLabel\": \"Pacific Records\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "]";

    // Restructuring JSON         
    public void Process() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        List<Festival> festivals = objectMapper.readValue(json, new TypeReference<List<Festival>>() {
        });

        Map<String, Map<String, List<String>>> output = new TreeMap<>(Comparator.nullsFirst(Comparator.naturalOrder()));
        for (Festival festival : festivals) {
            for (Band band : festival.bands) {
                output.compute(
                        null != band.getRecordLabel() ? band.getRecordLabel() : "",
                        (label, bandMapP) -> {
                            Map<String, List<String>> bandMap = bandMapP == null ?
                                    new TreeMap<>(Comparator.nullsFirst(Comparator.naturalOrder())) : bandMapP;
                            bandMap.compute(band.getName(), (bandName, festivalList) -> {
                                List<String> list = festivalList == null ? new ArrayList<>() : festivalList;
                                list.add(null != festival.getName() ? festival.getName() : "");
                                list.sort(Comparator.comparing(String::trim, Comparator.nullsFirst(Comparator.naturalOrder())));
                                return list;
                            });
                            return bandMap;
                        });
            }
        }
        printOutput(output);
    }

    // Print the output
    private void printOutput(Map<String, Map<String, List<String>>> output) {
        for (String key : output.keySet()) {
            System.out.println(key);
            for (String innerKey : output.get(key).keySet()) {
                System.out.println("     " + innerKey);
                for (String listItem : output.get(key).get(innerKey)) {
                    System.out.println("          " + listItem);
                }
            }
        }
    }
}
