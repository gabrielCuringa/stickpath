import configuration.GameConfiguration;
import exception.HeightNotValidException;
import exception.LineLgthHigherThanWidth;
import exception.WidthNotValidException;

import java.util.*;

public class Solution {

    /**
     * Main
     * Inputs (in that order): WIDTH, HEIGHT, DIAGRAM
     * Output: Connected pairs
     * @param args
     */
    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);

        try {
            // get WIDTH from console
            System.out.print("WIDTH: ");
            int width = in.nextInt();
            if(width <= GameConfiguration.WIDTH_MIN)
                throw new WidthNotValidException();

            // get HEIGHT from console
            System.out.print("HEIGHT: ");
            int height = in.nextInt();
            if(height > GameConfiguration.HEIGHT_MAX )
                throw new HeightNotValidException();

            if(in.hasNextLine()) {
               in.nextLine();
            }


            // use of linked hash map to maintain the order
            LinkedHashMap<String, Integer> topLabelToCursor = new LinkedHashMap<String, Integer>();
            LinkedHashMap<Integer, String> cursorToBottomLabel = new LinkedHashMap<Integer, String>();

            for(int i = 0; i < height; i++) {
                String line = in.nextLine();

                if(line.length() > width){
                    throw new LineLgthHigherThanWidth(i);
                }

                if(i == 0) {

                    // calculate start cursor for each top label
                    int startCursor = 0;
                    for (int j = 0; j < getLabels(line).size(); j++) {
                        topLabelToCursor.put(getLabels(line).get(j), startCursor);
                        startCursor += GameConfiguration.JUMP;
                    }
                } else if(i == height-1) {

                    // calculate start cursor for each bottom label
                    int startCursor = 0;
                    for (int y = 0; y < getLabels(line).size(); y++) {
                        cursorToBottomLabel.put(startCursor, getLabels(line).get(y));
                        startCursor += GameConfiguration.JUMP;
                    }
                } else {
                    // diagram body
                    int column = 0;
                    for (int ch = 0; ch < line.length(); ch++) {
                        if(line.charAt(ch) == '|'){
                            String label = topLabelToCursor.keySet().toArray()[column].toString();
                            int cursor = topLabelToCursor.get(label);

                            // is go to left possible
                            if(cursor > 0){
                                if(line.charAt(cursor-1) == '-'){
                                    topLabelToCursor.put(label, topLabelToCursor.get(label) - GameConfiguration.JUMP);
                                }
                            }

                            // is go to right possible
                            if(cursor != line.length()-1){
                                if(line.charAt(cursor+1) == '-'){
                                    topLabelToCursor.put(label, topLabelToCursor.get(label) + GameConfiguration.JUMP);
                                }
                            }

                            column ++;
                        }
                    }
                }

            }

            System.out.println("##################");
            System.out.println("##################");
            System.out.println("##### ANSWER #####");
            System.out.println("##################");
            System.out.println("##################");

            for (Map.Entry<String, Integer> entry: topLabelToCursor.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                System.out.println(key+cursorToBottomLabel.get(value));
            }
        } catch(Exception e){
            System.out.println("AN ERROR OCCURRED");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get labels from line
     * @param line : current line
     * @return labels
     */
    private static List<String> getLabels(String line){
        return Arrays.asList(line.split(String.format(" {%d}", GameConfiguration.HORIZONTAL_LINE_LENGTH)));
    }

}
