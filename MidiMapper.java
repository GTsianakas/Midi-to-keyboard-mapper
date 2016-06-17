import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MidiMapper{
		
	public MidiDevice dev;

	public MidiMapper(){

		Scanner input = new Scanner(System.in);
		int midiNum = 0;
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();

		System.out.println("\nDetected midi devices:");
		for (int i = 0; i < infos.length; i++){
			System.out.println(i+" : " +infos[i].getName() + " - " + infos[i].getDescription());
		}

		do{
			System.out.println("\nChoose Input device by number or ctrl-c to exit:");
			midiNum = input.nextInt();
		} while (!(midiNum >= 0 && midiNum < infos.length));

		try{
			dev = MidiSystem.getMidiDevice(infos[midiNum]);

	        List<Transmitter> transmitters = dev.getTransmitters();
			
			dev.open();

	        for (Transmitter trans : transmitters){
	        	trans.setReceiver(new MyReceiver());
	        }

	        Transmitter trans = dev.getTransmitter();
	        trans.setReceiver(new MyReceiver());

			
			System.out.println(dev.getDeviceInfo()+" Opened");


		}catch (MidiUnavailableException e){
			System.out.println("Can't retrieve device");
			e.printStackTrace();
		}	
	}

	public static void main(String[] args) {
		new MidiMapper();
	}
}

class MyReceiver implements Receiver{

	public void close(){}

	public void send(MidiMessage msg, long timestamp){
		System.out.println("midi message send");
	}
}