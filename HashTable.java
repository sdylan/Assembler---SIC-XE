class HashTable{

    public HashTableEntry[] arr;

    public int insertEntry (HashTableEntry entry){
        int insertPos = acquireHashCode(entry.getName());
        int colls = 0;
        while(true){
            if (arr[insertPos] == null){
                arr[insertPos] = entry;
                break;
            }
            else if (entry.getName().equals(arr[insertPos].getName())){

                return -1;
            }
            else{
                colls++;
                insertPos = resolveCollision(insertPos, colls);
            }
        }

        return insertPos;
    }

    public void findEntry (String str){
        int insertPos = acquireHashCode(str);
        int colls = 0;
        while(true)
            if (arr[insertPos] == null){
                System.out.print("ERROR " + str + " not found.\n");
                break;
            }
            else if (arr[insertPos].getName().equals(str)){
                //System.out.print(str + " found at position " + insertPos  + " with value " + arr[insertPos].getValue() +  ".\n");
                break;
            }
            else{
                colls++;
                insertPos = resolveCollision(insertPos, colls);
            }
    }





    public int acquireHashCode(String str){
        int g = 31;
        int hash = 0;
        for (int i = 0; i<str.length(); i++){
            hash = (g * hash + str.charAt(i)) % arr.length;
        }
        return hash;
    }

    public int resolveCollision(int pos, int colls){
        return (pos + colls * colls) % arr.length;
    }



}
