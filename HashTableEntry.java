class HashTableEntry {
    private String name;
    private String memLoc;

    public HashTableEntry(){

    }

    public HashTableEntry(String name, String memLoc){
        this.name = name;
        this.memLoc = memLoc;
    }

    public HashTableEntry(String name){
        this.name = name;
    }

    public String getName(){return name;}

    public void setName(String name){this.name = name;}

    public String getMemLoc(){return memLoc;}

    public void setMemLoc(String memLoc){this.memLoc = memLoc;}
}
