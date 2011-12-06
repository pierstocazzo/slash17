package interfaces;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class Serializzatore<T> extends Frame {
	private static final long	serialVersionUID	= 1L;
	
	// Metodo carica 
	@SuppressWarnings("unchecked")
	public T carica() {
		try {
			FileDialog fileDialog = new FileDialog(this, "Choose a file", FileDialog.LOAD);
			fileDialog.setVisible(true);
			String filename = fileDialog.getDirectory() + fileDialog.getFile();
			fileDialog.dispose();
			if (filename != null) {
				FileInputStream fis = new FileInputStream(filename);
				ObjectInputStream ois = new ObjectInputStream(fis);
				T object = (T) ois.readObject();
				fis.close();
				return object;
			}
		} catch (IOException a) {
			System.out.println("Errore lettura dell'oggetto");
		} catch (ClassNotFoundException e) {
			System.out.println("Classe non riconosciuta!!!");
		}
		return null;
	}
	
	// Metodo salva
	public void salva(T object) {
		try {
			FileDialog fileDialog = new FileDialog(this, "Choose a file", FileDialog.SAVE);
			fileDialog.setVisible(true);
			String filename = fileDialog.getDirectory() +  fileDialog.getFile();
			fileDialog.dispose();
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(object);
			oos.flush();
			fos.close();
		} catch (IOException e) {
			System.out.println("Classe non salvata!!!");
			e.printStackTrace();
		}
	}
	
	public T caricaFile(String filename) {
		try {
			if (filename != null) {
				FileInputStream fis = new FileInputStream(filename);
				ObjectInputStream ois = new ObjectInputStream(fis);
				T object = (T) ois.readObject();
				fis.close();
				return object;
			}
		} catch (IOException a) {
			System.out.println("Errore lettura dell'oggetto");
		} catch (ClassNotFoundException e) {
			System.out.println("Classe non riconosciuta!!!");
		}
		return null;
	}
}