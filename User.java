import java.util.ArrayList;

public class User {
  private String Username;
  private String Password;
  private String JoinDate;
  private int BlocksMined;
  private int EventsActivated;
  private ArrayList<Object[]> Inventory;
  private int lineNo;
  private String RarestOreDiscovered;
  private boolean toggleDelay;

    public User(String Username, String Password, String JoinDate, int BlocksMined, int EventsActivated, ArrayList<Object[]> Inventory) {
        this.Username = Username;
        this.Password = Password;
        this.JoinDate = JoinDate;
        this.BlocksMined = BlocksMined;
        this.EventsActivated = EventsActivated;
        this.Inventory = Inventory;
        this.lineNo = -1;
        this.toggleDelay = true;
        this.RarestOreDiscovered = "None";
      }
  public User(String Username, String Password, String JoinDate, int BlocksMined, int EventsActivated, ArrayList<Object[]> Inventory,int lineNo,boolean toggleDelay, String RarestOreDiscovered) {
    this.Username = Username;
    this.Password = Password;
    this.JoinDate = JoinDate;
    this.BlocksMined = BlocksMined;
    this.EventsActivated = EventsActivated;
    this.Inventory = Inventory;
    this.lineNo = lineNo;
    this.toggleDelay = toggleDelay;
    this.RarestOreDiscovered = RarestOreDiscovered;
  }

  public String toFileFormat(Boolean showPassword) {
    return "|| " + Username + " {\n" +
      "Password = \"" + (showPassword == true ? Password:"*****") + "\"\n" +
      "JoinDate = \"" + JoinDate + "\"\n" +
      "BlocksMined = " + BlocksMined + "\n" +
      "EventsActivated = " + EventsActivated + "\n" + 
      "toggleDelay = " + toggleDelay + "\n" +
      "Rarest ore discovered = " + RarestOreDiscovered + "\n" +
      "Inventory = " + InventoryToString() + "\n" +
      "}\n";
  }

    private String InventoryToString() {
        if (Inventory == null || Inventory.isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (Object[] item : Inventory) {
            sb.append("(");
            for (int i = 0; i < item.length; i++) {
                sb.append(item[i]);
                if (i < item.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("), ");
        }
        return sb.substring(0, sb.length() - 2) + "]";
    }

  public String getPassword() {
      return Password;
  }
  public String getUsername() {
      return Username;
  }
  public int getLineNo() {
      return lineNo;
  }
    public boolean gettoggleDelay() {
          return toggleDelay;
      }
    public void setRarestOreDiscovered(String ore) {
        this.RarestOreDiscovered = ore;
    }
    public int getBlocksMined() {
        return this.BlocksMined;
    }
    public int getEventsActivated() {
        return this.EventsActivated;
    }
    public ArrayList<Object[]> getInventory() {
        return this.Inventory;
    }

    public void setLineNo(int lineNo) {
      this.lineNo = lineNo;
    }
    public void setInventory(ArrayList<Object[]> Inventory) {
      this.Inventory = Inventory;
    }
    public void setBlocksMined(int BlocksMined) {
        this.BlocksMined = BlocksMined;
    }
    public void setEventsActivated(int EventsActivated) {
        this.EventsActivated = EventsActivated;
    }
}