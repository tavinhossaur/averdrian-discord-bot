package model;

public class Champion {
    
    // Atributos do Champion
    private final String name;
    private final String quote1;
    private final String quote2;
    private final String emotes;

    // Construtor do Champion
    public Champion(String name, String quote1, String quote2, String emotes) {
        this.name = name;
        this.quote1 = quote1;
        this.quote2 = quote2;
        this.emotes = emotes;
    }

    // Getters (setters não são necessários pois não haverá alteração dos dados, apenas o seu retorno)
    public String getName() {
        return name;
    }

    public String getQuote1() {
        return quote1;
    }

    public String getQuote2() {
        return quote2;
    }

    public String getEmotes() {
        return emotes;
    }
}
