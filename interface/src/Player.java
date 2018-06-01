public class Player {

    private String name;
    private Boolean IA;

    public Player(String name, Boolean IA){
        this.name = name;
        this.IA = IA;
    }

    public String getName() {
        return name;
    }

    public Boolean getIA() {
        return IA;
    }
}
