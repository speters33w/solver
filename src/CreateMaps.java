import kivaworld.KeyboardResource;
import solver.CreateMap;

import java.io.IOException;

public class CreateMaps {
    public static void main(String[] args) throws IOException {
        CreateMap createMap = new CreateMap();
        String in = "";
        while (!in.equalsIgnoreCase("Q")) {
            String map = createMap.randomMapString();
            System.out.println(map);
            System.out.println("Save map?");
            KeyboardResource keyboardResource = new KeyboardResource();
            in = keyboardResource.getLine();
            if (keyboardResource.yes(in)) { //not available in original KivaWorld package
                createMap.saveFile(map);
            } else if (in.equalsIgnoreCase("q")) {
                keyboardResource.close(); //not available in original KivaWorld package
                break;
            }
        }
    }
}
