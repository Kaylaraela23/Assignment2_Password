package Packy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ScrabbleApp extends JFrame {

	private JTextField txtTiles;
	private JTextArea txtOutput;
	private JButton btnGenerate;
	private static final String PLACEHOLDER_TEXT = "Write your 7 letters here, no more than 7, y'hear?";
	private static final String OUTPUT_DEFAULT_TEXT = "Your scramble will appear here <3";

	/**
	 * Create the frame.
	 */
	public ScrabbleApp() {
		setTitle("Scrabble App");
		setBounds(100, 100, 600, 400); // Set frame size
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		// Initialize and set the size of the input field
		txtTiles = new JTextField(PLACEHOLDER_TEXT);
		txtTiles.setPreferredSize(new Dimension(500, 40)); // Larger input field

		// Add focus listener to handle placeholder text
		txtTiles.addFocusListener(new FocusListener() {
	
			public void focusGained(FocusEvent e) {
				if (txtTiles.getText().equals(PLACEHOLDER_TEXT)) {
					txtTiles.setText("");
					txtTiles.setForeground(Color.BLACK);
				}
			}

	
			public void focusLost(FocusEvent e) {
				if (txtTiles.getText().isEmpty()) {
					txtTiles.setText(PLACEHOLDER_TEXT);
					txtTiles.setForeground(Color.GRAY);
				}
			}
		});

		// Set placeholder text color
		txtTiles.setForeground(Color.GRAY);
		getContentPane().add(txtTiles, BorderLayout.NORTH);

		// Initialize and set the size of the output area
		txtOutput = new JTextArea();
		txtOutput.setEditable(false);
		txtOutput.setLineWrap(true);
		txtOutput.setWrapStyleWord(true);
		txtOutput.setPreferredSize(new Dimension(500, 150)); // Smaller output area
		txtOutput.setText(OUTPUT_DEFAULT_TEXT); // Default text
		JScrollPane scrollPane = new JScrollPane(txtOutput);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		// Initialize and set the size of the button
		btnGenerate = new JButton("Click here to generate your scrambles");
		btnGenerate.setPreferredSize(new Dimension(300, 30)); 
		getContentPane().add(btnGenerate, BorderLayout.SOUTH);

		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateScrambles();
			}
		});
	}

	// Generates and displays all scrambles of the tiles.

	private void generateScrambles() {
		String input = txtTiles.getText().trim();

		// Debugging statement to check the input
		System.out.println("Input: " + input);

		if (input.equals(PLACEHOLDER_TEXT) || input.isEmpty()) {
			txtOutput.setText("Error: Enter 7 letters, bruh.");
			return;
		}

		if (input.length() > 7) {
			txtOutput.setText("Error: More than 7 tiles entered. Can you count?");
			return;
		}
		if (!input.matches("[a-zA-Z]*")) {
			txtOutput.setText("Error: Only alphabetic characters are allowed. Do we need to revisit Kindergarten?");
			return;
		}

		List<String> scrambles = scramble(input);

		// Debugging statement to check scrambles list
		System.out.println("Scrambles: " + scrambles);

		if (scrambles.isEmpty()) {
			txtOutput.setText("No scrambles available.");
		} else {
			txtOutput.setText("Scrambles:\n" + String.join("\n", scrambles));
		}
	}

	// Generates scrambles of a string.

	private List<String> scramble(String str) {
		List<String> results = new ArrayList<>();
		scrambleHelper("", str, results);
		return results;
	}

	private void scrambleHelper(String prefix, String str, List<String> results) {
		int n = str.length();
		if (n == 0) {
			results.add(prefix);
		} else {
			for (int i = 0; i < n; i++) {
				String newPrefix = prefix + str.charAt(i);
				String remaining = str.substring(0, i) + str.substring(i + 1);
				scrambleHelper(newPrefix, remaining, results);
			}
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				ScrabbleApp frame = new ScrabbleApp();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}

/*
 * Test 1: Functioning
 * Input: Pizza
Scrambles: [Pizza, Pizaz, Pizza, Pizaz, Piazz, Piazz, Pziza, Pziaz, Pzzia, Pzzai, Pzaiz, Pzazi, Pziza, Pziaz, Pzzia, Pzzai, Pzaiz, Pzazi, Paizz, Paizz, Paziz, Pazzi, Paziz, Pazzi, iPzza, iPzaz, iPzza, iPzaz, iPazz, iPazz, izPza, izPaz, izzPa, izzaP, izaPz, izazP, izPza, izPaz, izzPa, izzaP, izaPz, izazP, iaPzz, iaPzz, iazPz, iazzP, iazPz, iazzP, zPiza, zPiaz, zPzia, zPzai, zPaiz, zPazi, ziPza, ziPaz, zizPa, zizaP, ziaPz, ziazP, zzPia, zzPai, zziPa, zziaP, zzaPi, zzaiP, zaPiz, zaPzi, zaiPz, zaizP, zazPi, zaziP, zPiza, zPiaz, zPzia, zPzai, zPaiz, zPazi, ziPza, ziPaz, zizPa, zizaP, ziaPz, ziazP, zzPia, zzPai, zziPa, zziaP, zzaPi, zzaiP, zaPiz, zaPzi, zaiPz, zaizP, zazPi, zaziP, aPizz, aPizz, aPziz, aPzzi, aPziz, aPzzi, aiPzz, aiPzz, aizPz, aizzP, aizPz, aizzP, azPiz, azPzi, aziPz, azizP, azzPi, azziP, azPiz, azPzi, aziPz, azizP, azzPi, azziP]

* Test 2: Too Many Letters
* Input: PizzaPiea
* Error: More than 7 tiles entered. Can you count?

* Test 3: Not enough Letters
* Error: Enter 7 letters, bruh.

* Test 4:Non-letter characters entered
* Input: 2345df
* Error: Only alphabetic characters are allowed. Do we need to revisit Kindergarten?
 */