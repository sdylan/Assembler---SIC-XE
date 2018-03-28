class Instruction{

        public String line, label, mnemonic, operand, address, opCode, objCode, error = "";
        public boolean comment=false, literal=false, indirect=false, immediate=false, indexed=false, extended=false;
        public int format, displacement;

        public Instruction(String line, String operand, String mnemonic){

                this.line = line;

                this.label = operand;

                this.operand = operand;

                this.mnemonic = mnemonic;
        }

        public Instruction(String line){

                this.line = line;

                if(line.charAt(0)=='.')
                        comment = true;

                if(!comment){

                        label = line.substring(0,8).trim();

                        if(line.length()>19){
                                mnemonic = line.substring(10,17).trim();

                                switch(line.charAt(18)){

                                        case '#': immediate = true;
                                                  break;
                                        case '@': indirect = true;
                                                  break;
                                        case '=': literal = true;
                                                  break;
                                }

                                if(line.length()>31)
                                        operand = line.substring(19,29).trim();
                                else
                                        operand = line.substring(19).trim();
                        }

                        else{
                                mnemonic = line.substring(10).trim();
                                operand = "";
                        }

                        if (line.charAt(9)=='+')
                                extended = true;

                        if(operand.contains(",X"))
                                indexed = true;
                }
        }
}
