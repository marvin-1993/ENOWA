package stringGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Class to create random Strings.
 * @author Marvin
 */
public class StringGenerator {

  public static String[] generateString(int length) {

    List<String> arrays = Arrays
        .asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "m", "n", "p", "r",
        "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "J",
         "K", "M", "N", "P", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3",
         "4", "5", "6", "7", "8", "9");
    String[] attribute = {"","","","","","","","","",""};

    for (int a = 0; a < 10; a++) {
      for (int i = 0; i < length; i++) {
        attribute[a] += arrays.get(new Random().nextInt(arrays.size()));
      }
    }
    return attribute;
  }

}
