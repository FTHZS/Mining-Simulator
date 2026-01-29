import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Mineral {
  private String mineralName;
  private Double baseRarity;
  private Dialogue spawnMessage;
  private Integer normalAmount;
  private Integer ionizedAmount;
  private Integer spectralAmount;
  private Integer transdimensionalAmount;
  private Boolean unlocked;
  private String collection;
  private static ArrayList<Object[]> Inventory;
  private static String eventActive;
  private static int eventBlocksLeft; 
  private static boolean toggleDelay;

  static {
    eventActive = "None";
    eventBlocksLeft = 0;
    toggleDelay = true;
  }

  public Mineral(String mineralName, Integer baseRarity, String collection, Dialogue spawnMessage) {
    this.mineralName = mineralName;
    this.baseRarity = (double)baseRarity;
    this.spawnMessage = spawnMessage;
    this.normalAmount = 0;
    this.ionizedAmount = 0;
    this.spectralAmount = 0;
    this.transdimensionalAmount = 0;
    this.unlocked = false;
    this.collection = collection;
  }
  public Mineral(String mineralName, Integer baseRarity, String collection) {
    this.mineralName = mineralName;
    this.baseRarity = (double)baseRarity;
    this.normalAmount = 0;
    this.ionizedAmount = 0;
    this.spectralAmount = 0;
    this.transdimensionalAmount = 0;
    this.unlocked = false;
    this.collection = collection;
  }

  public void mineNormal(Integer amount) {
    this.normalAmount += amount;
  }

  public void mineIonized(Integer amount) {
    this.ionizedAmount += amount;
  }

  public void mineSpectral(Integer amount) {
    this.spectralAmount += amount;
  }

  public void mineTransdimensional(Integer amount) {
    this.transdimensionalAmount += amount;
  }

  static String[][] Events = new String[][] {
    {"Earth","1","1"},
    {"Imaginary","30","10"},
    {"Air","100","20"},
  };
  static Mineral[] Index = new Mineral[] {
      new Mineral("Stone", 2, "Earth"),
      new Mineral("Coal", 5, "Earth"),
      new Mineral("Zinc", 6, "Earth"),
      new Mineral("Copper", 10, "Earth"),
      new Mineral("Iron", 20, "Earth"),
      new Mineral("Silver", 21, "Earth"),
      new Mineral("Quartz", 24, "Earth"),
      new Mineral("Granite", 40, "Earth"),
      new Mineral("Opal", 63, "Earth"),
    new Mineral("Gold", 96, "Earth", new Dialogue(new Object[][] {
      {"A faint yellow glow flickers from within the rock.", 10}
    }, 100)),
    new Mineral("Topaz", 112, "Earth", new Dialogue(new Object[][] {
      {"A warm, golden light emanates from the rock as you chip away.", 10},
      {"You see flashes of yellow within the stone.", 10}
    }, 100)),
    new Mineral("Obsidian", 120, "Earth", new Dialogue(new Object[][] {
      {"The stone gleams with a dark, glassy sheen.", 15},
      {"Its surface is smooth yet sharp to the touch.", 15}
    }, 100)),
    new Mineral("Sapphire", 140, "Earth", new Dialogue(new Object[][] {
      {"A deep blue light reflects off the stone's surface.", 20},
      {"It feels cool to the touch, as though it holds secrets.", 20}
    }, 100)),
    new Mineral("Ruby", 144, "Earth", new Dialogue(new Object[][] {
      {"A brilliant red hue shines brightly within the rock.", 25},
      {"You feel a warm pulse emanating from deep inside.", 25}
    }, 100)),
    new Mineral("Tungsten", 180, "Earth", new Dialogue(new Object[][] {
      {"The stone seems almost impossibly dense and hard.", 30},
      {"It feels like it could withstand anything.", 30}
    }, 100)),
    new Mineral("Emerald", 210, "Earth", new Dialogue(new Object[][] {
      {"A vibrant green glow pulses softly from within the stone.", 35},
      {"It feels alive with energy as you hold it.", 35}
    }, 100)),
    new Mineral("Bedrock", 288, "Earth", new Dialogue(new Object[][] {
      {"The rock beneath you feels impossibly solid, unyielding.", 40},
      {"You sense a deep, ancient power flowing within it.", 40}
    }, 100)),
    new Mineral("Diamond", 315, "Earth", new Dialogue(new Object[][] {
      {"A faint sparkle catches your eye from deep inside the rock.", 45},
      {"The stone seems to shine even in the dark.", 45}
    }, 100)),
    new Mineral("Platinum", 420, "Earth", new Dialogue(new Object[][] {
      {"A silvery sheen shines brightly as you uncover the mineral.", 50},
      {"It seems to reflect the light in a way no other metal does.", 50}
    }, 100)),
    new Mineral("Radium", 504, "Earth", new Dialogue(new Object[][] {
      {"A faint, eerie glow emanates from the stone's core.", 55},
      {"The air around you feels charged with an unnatural force.", 55}
    }, 100)),
    new Mineral("Plutonium", 672, "Earth", new Dialogue(new Object[][] {
      {"A strange, sickly green light emanates from deep within the rock.", 60},
      {"You feel a sudden chill run through you as you touch it.", 60},
      {"It radiates with a terrifying energy that feels almost alive.", 60}
    }, 100)),
    new Mineral("Stalacite", 60, "Imaginary", new Dialogue(new Object[][] {
      {"It seems sharp.",10}
    },100)),
    new Mineral("Crystobalite", 70, "Imaginary"),
    new Mineral("Moissonite", 90, "Imaginary"),
    new Mineral("Manatite", 126, "Imaginary"),
    new Mineral("Ambrosia", 160, "Imaginary"),
    new Mineral("Duratite", 168, "Imaginary"),
    new Mineral("Runestone", 224, "Imaginary"),
    new Mineral("Adamantine", 336, "Imaginary"),
    new Mineral("Darwinium", 480, "Imaginary"),
    new Mineral("Stormium", 48, "Air"),
    new Mineral("Electrix", 84, "Air"),
    new Mineral("Tempest stone", 224, "Air"),
    new Mineral("Illusil", 360, "Air"),
    new Mineral("Aetherium", 630, "Air"),
  };

  private static String TriggerEvent() {
    int cumulative = 0;
    int eventID = 0;
    int poolSize = 0;

    int[] eventRarityValues = new int[Events.length];
    for (int i = 0; i < Events.length; i++) {
      eventRarityValues[i] = Integer.parseInt(Events[i][1]);
    }
    int LCM = LCMCalculator.lcmOfArray(eventRarityValues);

    for (int i = 1; i < Events.length; i++) {
      poolSize += LCM/Integer.parseInt(Events[i][1]);
    }
    //System.out.println("poolsize: "+poolSize);
    if (poolSize != LCM) {
      Events[0][2] = Double.toString((double)LCM/(LCM-poolSize));
      poolSize += LCM/Integer.parseInt(Events[0][1]);
    }
    //System.out.println("Earth R: "+Events[0][1]);
    //System.out.println("Earth number in pool: "+LCM/Integer.parseInt(Events[0][1]));

    int chosen = new Random().nextInt(poolSize)+1;
    while (cumulative < chosen) {
      cumulative += LCM/Integer.parseInt(Events[eventID][1]);
      eventID++;
    } eventID--;

    //System.out.println("poolsize: "+poolSize+", gotten: "+Events[eventID][0]);
    if (Events[eventID][0] == "Earth") {
      eventActive = "None";
      eventBlocksLeft = 0;
    } else {
      eventActive = Events[eventID][0];
      eventBlocksLeft = Integer.parseInt(Events[eventID][2]);
      System.out.println("\u001B[46mYou activated "+Events[eventID][0]+" ores!\u001B[0m");
      Mineral.appendEventsActivated(Main.getUser(),1);
    }
    return Events[eventID][0];
  }

  public static void Mine() {
    //--Initialize
    int cumulative = 0;
    int oreID = 0;
    int poolSize = 0;
    eventBlocksLeft--;

    if (eventBlocksLeft == -1){
      String event = TriggerEvent();
      
    } else {
      if (eventBlocksLeft == 0) {eventActive = "None";}
    }
    Mineral[] temp;
    ArrayList<Mineral> eventMinerals = new ArrayList<Mineral>();
    for (Mineral mineral : Index) {
        if (mineral.collection.equals(eventActive)||mineral.collection.equals("Earth")) {
            eventMinerals.add(mineral);
        }
    }
    temp = eventMinerals.toArray(new Mineral[0]);
    Mineral[] Index = temp;
    
    //int LCM = 10080;
    //--Calculate LCM
    int[] baseRarityValues = new int[Index.length];
    for (int i = 0; i < Index.length; i++) {
        //System.out.print(Index[i].mineralName+", ");
        baseRarityValues[i] = Index[i].baseRarity.intValue();
    }
    int LCM = LCMCalculator.lcmOfArray(baseRarityValues);
    //System.out.println("LCM: " + LCM);

    //--Calculate poolSize
    for (int i =1; i < Index.length; i++) {
      poolSize += LCM / Index[i].baseRarity;
    }
    //System.out.println("poolsize: "+poolSize);
    if (poolSize != LCM) {
      Index[0].baseRarity = (double)LCM/(LCM-poolSize);
      poolSize += LCM/Index[0].baseRarity;
    }
    //System.out.println("Stone R: "+Index[0].baseRarity);
    //System.out.println("Stone number in pool: "+LCM/Index[0].baseRarity);
    
    //--Calculate gotten mineral
    int chosen = new Random().nextInt(poolSize)+1;
    //System.out.println("random: "+chosen);
    //System.out.println("poolsize: "+poolSize);
    while (cumulative < chosen) {
      cumulative += LCM/Index[oreID].baseRarity;
      oreID++;
    } oreID--;
    //System.out.println("cumulative: "+cumulative);
    //System.out.println("oreID: "+oreID);
    if (Index[oreID].unlocked == false) {
      Index[oreID].unlocked = true;
      Inventory.add(new Object[] {Index[oreID].mineralName,0,0,0,0});
    }
    if (Index[oreID].spawnMessage != null) {
      Index[oreID].spawnMessage.show(toggleDelay);
    }
    System.out.println("\u001B[40mYou found " + Index[oreID].mineralName + "!\u001B[0m");

    int subrarityChosen = new Random().nextInt(1000)+1;

    if (subrarityChosen == 1000) {
      Index[oreID].mineTransdimensional(1);
      //System.out.println("\u001B[37m\u001B[42mit is TRANSDIMENSIONAL [1 in " + Index[oreID].baseRarity.intValue() * 1000 + "]");
      System.out.print("\u001B[37m\u001B[42m");
      new Dialogue(new Object[][] {
        {"it is TRANSDIMENSIONAL [1 in " + Index[oreID].baseRarity.intValue() * 1000 + "]",70}
      },500).show(true);
    } else if (subrarityChosen > 989) {
      Index[oreID].mineSpectral(1);
      //System.out.println("\u001B[43mit is Spectral!!! [1 in " + Index[oreID].baseRarity.intValue() * 100 + "]");
      System.out.print("\u001B[43m");
      new Dialogue(new Object[][] {
        {"it is Spectral!!! [1 in " + Index[oreID].baseRarity.intValue() * 100 + "]",50}
      },300).show(true);
    } else if (subrarityChosen > 879) {
      Index[oreID].mineIonized(1);
      //System.out.println("\u001B[93mit is Ionized! [1 in " + Index[oreID].baseRarity.intValue() * 10 + "]");
      System.out.print("\u001B[93m");
      new Dialogue(new Object[][] {
        {"it is Ionized! [1 in " + Index[oreID].baseRarity.intValue() * 10 + "]",30}
      },100).show(toggleDelay);
    } else {
      Index[oreID].mineNormal(1);
      System.out.println("it is normal. [1 in " + Index[oreID].baseRarity.intValue() + "]");
    }
    System.out.print("\u001B[0m");
    if (Index[oreID].spawnMessage != null||subrarityChosen>878) {
      if (toggleDelay == true) {new Scanner(System.in).next();}
    }
    
  }

  public static ArrayList<Object[]> getInventory() {
    return Inventory;
  }
  public static void setInventory(ArrayList<Object[]> Inventory) {
      Mineral.Inventory = Inventory;

      for (int i = 0; i < Inventory.size(); i++) {
          Object[] mineralData = Inventory.get(i);

          String mineralName = (String) mineralData[0];
          Integer normalAmount = (Integer) mineralData[1];
          Integer spectralAmount = (Integer) mineralData[2];
          Integer ionizedAmount = (Integer) mineralData[3];
          Integer transdimensionalAmount = (Integer) mineralData[4];

          for (int j = 0; j < Index.length; j++) {
              if (Index[j].mineralName.equals(mineralName)) {
                  Index[j].normalAmount = normalAmount;
                  Index[j].spectralAmount = spectralAmount;
                  Index[j].ionizedAmount = ionizedAmount;
                  Index[j].transdimensionalAmount = transdimensionalAmount;
                  break; 
              }
          }
      }
  }
  public static void setInventory() {
      Inventory = new ArrayList<Object[]>();
      for (int i = 0; i < Index.length; i++) {
          Object[] mineralData = new Object[] {
              Index[i].mineralName,             
              Index[i].normalAmount,          
              Index[i].spectralAmount,           
              Index[i].ionizedAmount,            
              Index[i].transdimensionalAmount 
          };

          Inventory.add(mineralData);
      }
  }
  public static void printInventory() {
      setInventory();
      System.out.println("\u001B[4mInventory\u001B[0m");
      for (Object[] item : Inventory) {
          // Cast the elements of the array to Integer
          int normalAmount = (int) item[1];
          int ionizedAmount = (int) item[2];
          int spectralAmount = (int) item[3];
          int transdimensionalAmount = (int) item[4];

          // Print the mineral name
          System.out.print(item[0] + ": ");

          // Print only the non-zero amounts
          if (normalAmount > 0) {
              System.out.print("\u001B[90m"+normalAmount + " Normal\u001B[0m ");
          }
          if (ionizedAmount > 0) {
              System.out.print("\u001B[93m"+ionizedAmount + " Ionized\u001B[0m ");
          }
          if (spectralAmount > 0) {
              System.out.print("\u001B[43m"+spectralAmount + " Spectral\u001B[0m ");
          }
          if (transdimensionalAmount > 0) {
              System.out.print("\u001B[37m\u001B[42m"+transdimensionalAmount + " Transdimensional\u001B[0m ");
          }

          System.out.println();
      }
  }
  public static void printIndex() {
    System.out.println("\u001B[4mIndex\u001B[0m");
    for (int i = 0; i<Index.length;i++) {
      System.out.println("\u001B[1m"+Index[i].mineralName+"\u001B[0m\nCollection: "+Index[i].collection+"\nNormal [1 in "+Index[i].baseRarity+"]\nIonized [1 in " + Index[i].baseRarity.intValue()*10 + "]\nSpectral [1 in " + Index[i].baseRarity.intValue()*100 + "]\nTransdimensional [1 in " + Index[i].baseRarity.intValue()*1000 + "]\nSpawn Message: "+Index[i].spawnMessage+"\n");
    }
  }
  public static String getEventActive() {
    return eventActive;
  }
  public static int getEventBlocksLeft() {
    return eventBlocksLeft;
  }
  public static void appendEventsActivated(User user, int n) {
    user.setEventsActivated(user.getEventsActivated() + n);
  }
  public static String getRarestOreDiscovered(ArrayList<Object[]> Inventory){
    /*int[] rarityValues = new int[Inventory.size()];
    int max = 0;
    int maxIndex = 0;
    for (int i = 0; i < Inventory.size(); i++) {
      Object[] mineralData = (Object[]) Inventory.get(i);
      if (Integer.parseInt(mineralData[1].toString())>max) {
        max = Integer.parseInt(mineralData[1].toString());
        maxIndex = i;
      }
      rarityValues[i] = Integer.parseInt(mineralData[1].toString());*/
    int max = 0;
    int maxIndex = 0;
    String subRarity = "";
    for (int i = 0; i < Inventory.size(); i++) {
      String collection = Index[i].collection;
      int c = 0;
      for (; c<Events.length;c++){
        if (Events[c][0] == collection) {
          break;
        }
      }
      int eventRarity = (Integer.parseInt(Events[c][2])/Integer.parseInt(Events[c][1]));
      int baseRarity = Index[i].baseRarity.intValue() * eventRarity;
      int normalAmount = (Integer) Inventory.get(i)[1];
      int ionizedAmount = (Integer) Inventory.get(i)[2];
      int spectralAmount = (Integer) Inventory.get(i)[3];
      int transdimensionalAmount = (Integer) Inventory.get(i)[4];
      int normalRarity = normalAmount>0?baseRarity:0;
      int IonizedRarity = ionizedAmount>0?baseRarity*10:0;
      int SpectralRarity = spectralAmount>0?baseRarity*100:0;
      int TransdimensionalRarity = transdimensionalAmount>0?baseRarity*1000:0;
      if (normalRarity>max||IonizedRarity>max||SpectralRarity>max||TransdimensionalRarity>max) {
        maxIndex = i;
        max = TransdimensionalRarity > 0 ? TransdimensionalRarity : SpectralRarity > 0 ? SpectralRarity : IonizedRarity > 0 ? IonizedRarity : normalRarity;
        subRarity = (max == normalRarity) ? "Normal" : (max == IonizedRarity) ? "Ionized" : (max == SpectralRarity) ? "Spectral" : "Transdimensional";
      }
    }
    return subRarity+" "+Inventory.get(maxIndex)[0].toString()+" [1 in "+max+"]";
      
  }
  public static void toggledelay() {
    toggleDelay = !toggleDelay;
    System.out.println("Toggle delay: "+toggleDelay);
  }
  public static void setToggleDelay(boolean toggle) {
    toggleDelay = toggle;
  }
  public static boolean getToggledelay() {
    return toggleDelay;
  }
}