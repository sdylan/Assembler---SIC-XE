import java.util.*;
import java.io.*;

public class AssemblerApp{

	public static void main (String[] args) throws Exception{
		Scanner sc = new Scanner(new File(args[0]));
		Scanner msc = new Scanner(new File("mnemonics.txt"));
		Assembler ass = new Assembler();
		
		int rindex = 0;

		while(msc.hasNext()){
			String[] tempstr = msc.nextLine().split("[ \t]+");

			if(tempstr.length==3)
				ass.mnemonics[ass.mindex++] = new Mnemonic(tempstr);
			if(tempstr.length==2)
				ass.registers[rindex++] = new Register(tempstr);	
		}
	
		ass.labels.arr = new HashTableEntry[997];

		while(sc.hasNext()){
			ass.instructions[ass.index++] = new Instruction(sc.nextLine());
			ass.getAddress(ass.instructions[ass.index-1]);
		}

		for( int i = 0; i<ass.index; i++){
//			ass.getObjectCode(ass.instructions[i]);
		}
		
		ass.printListing(ass.index);
		ass.printHashTable();
	}	
}
