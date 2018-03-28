import java.util.*;
import java.io.*;


public class Assembler{	
		
	public static Instruction[] instructions = new Instruction[100];
	public static Mnemonic[] mnemonics = new Mnemonic[60];
	public static Register[] registers = new Register[10];
	public static HashTable	labels = new HashTable();
	public static Instruction[] literalPool = new Instruction[30];
	public static int address, index = 0, mindex = 0;
	

	public static void main (String[] args) throws Exception{
		Scanner sc = new Scanner(new File(args[0]));
		Scanner msc = new Scanner(new File("mnemonics.txt"));

		int rindex = 0;

		while(msc.hasNext()){
			String[] tempstr = msc.nextLine().split("[ \t]+");

			if(tempstr.length==3)
				mnemonics[mindex++] = new Mnemonic(tempstr);
			if(tempstr.length==2)
				registers[rindex++] = new Register(tempstr);	
		}
	
		labels.arr = new HashTableEntry[997];

		while(sc.hasNext()){
			instructions[index++] = new Instruction(sc.nextLine());
			getAddress(instructions[index-1]);
		}

		for( int i = 0; i<index; i++){
//			getObjectCode(instructions[i]);
		}
		
		printListing(index);
		printHashTable();
	}
	
	public static void printHashTable(){
		
		System.out.println();
		System.out.println("SYMBOL TABLE");
		System.out.println("Table Loc\t   Label   \tAddress");
		System.out.println("---------\t-----------\t-------");
		for(int i = 0; i < labels.arr.length; i++){
			if(labels.arr[i] != null)
				System.out.println(String.format("%9s", i) + String.format("\t%-11s", labels.arr[i].getName()) + String.format("\t%7s", labels.arr[i].getMemLoc()));
		}
		System.out.println();
	}
	
	public static void printListing(int index){

		System.out.println();
		System.out.println("Address  Object Code\tSource Code");
		System.out.println("-------  -----------\t-----------");

		for(int i=0;  i<index; i++){
			
			if (instructions[i].comment)
				System.out.println("\t\t\t" + instructions[i].line);
			else	
				System.out.println(String.format("%7s", instructions[i].address) + ((instructions[i].objCode!=null)?("  " + instructions[i].objCode + "    \t"):"           \t") + instructions[i].line + instructions[i].error);

		}	
	}
	
	public static void addLiteral(String operand){
		
		int i = 0;
		while(literalPool[i++]!=null);
		String line = String.format("=%-7s", (operand.length()>=7)?operand.substring(0,7):operand) + "  BYTE     " + operand;
		literalPool[i-1] = new Instruction(line, operand, "BYTE");
	}

	public static void dumpPool(){
		
		int i = 0;
		while(literalPool[i++]!=null){
			instructions[index] = literalPool[i-1];
			literalPool[i-1] = null;
			getAddress(instructions[index]);
		}
	}
/*
	public static void getObjectCode(Instruction instr){
		
		if(instr.opCode != null){
			int opNum = Integer.parseInt(instr.opCode, 16);		

			if(instr.immediate) opNum += 1;

			else if(instr.indirect) opNum +=2;

			else opNum +=3;
			
			if(opNum < 16) instr.objCode = "0" + Integer.toHexString(opNum).toUpperCase();
			else instr.objCode = Integer.toHexString(opNum).toUpperCase() + "00";
						
			getDisplacement(instr);			

			if (instr.indexed){
					
			}
		}
	}	

	public static String getDisplacement(Instruction instr){
		
		int pc = Integer.parseInt(instr.address) + instr.format;
		
		for(int i = 0; i<labels.arr.length; i++){
			if(instr.operand.equals(labels.arr[i].getName())){
				instr.displacement = Integer.parseInt(labels.arr[i].getMemLoc()) - pc;
			}
			//if(instr.displacement > 2047)   COME BACK TO THIS HERE
		}				
		return null;
	}	
*/	
	public static void getAddress(Instruction instr){

		if(!instr.comment){
			if(instr.mnemonic.equals("START")){
				address = Integer.parseInt(instr.operand, 16);
				instr.address = Integer.toHexString(address).toUpperCase();
			}	
			
			else 	
				instr.address = Integer.toHexString(address).toUpperCase();
					
			if(instr.literal){
				if(instr.operand.charAt(0) == 'X' && instr.operand.length()%2 == 0)
					instr.error += "\n\tERROR: The hexadecimal literal has an odd amount of digits.";
				else
					addLiteral(instr.operand);
			}

			//else if(EQU
				
			if(instr.mnemonic.equals("WORD")){
				address+=3;	
			}		

			else if(instr.mnemonic.equals("END")||instr.mnemonic.equals("LTORG")){
				dumpPool();
			}

			else if(instr.mnemonic.equals("BASE")){
			}
		
			else if(instr.mnemonic.equals("RESW")){
				address+=3*Integer.parseInt(instr.operand);	
			}		
						
			else if(instr.mnemonic.equals("RESB")){
				address+=Integer.parseInt(instr.operand);	
			}
		
			else if(instr.mnemonic.equals("BYTE")){
				if(instructions[index].operand.charAt(0) == 'X')
					address += (instructions[index++].operand.length()-3)/2;
				else if(instructions[index].operand.charAt(0) == 'C')
					address += (instructions[index++].operand.length()-3); 
				
			}		

			else{
				for(int j = 0; j< mindex; j++){
					if(instr.mnemonic.equals(mnemonics[j].name)){
						instr.format = mnemonics[j].format;
						instr.opCode = mnemonics[j].opCode;
						if(instr.extended) instr.format++;
						address+=instr.format;						}
					}
			}
			
			if(!instr.label.equals("")){
				int pos = labels.insertEntry(new HashTableEntry(instr.label, instr.address));
					if(pos==-1){
						instr.error += "\n\tERROR: The label is already being used.";
					}
			}
		}
	}
}
