class Mnemonic{

        public String name, opCode;
        public int format;

        public Mnemonic(String[] strarr){

                name = strarr[0];
                opCode = strarr[1];
                format = Integer.parseInt(strarr[2]);
        }
}
