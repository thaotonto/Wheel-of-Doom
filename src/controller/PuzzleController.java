package controller;

import gui.GameFrame;
import gui.MainPanel;
import puzzle.Puzzle;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static javax.swing.JOptionPane.OK_OPTION;

/**
 * Created by Hoang on 4/4/2017.
 */
public class PuzzleController {
    private ArrayList<Puzzle> puzzleList = new ArrayList<>();
    private boolean isValidTheme=true;

    public boolean isValidTheme() {
        return isValidTheme;
    }

    public PuzzleController(String theme, int playerNo) {
        String themeUrl = "resources/theme/" + theme + ".txt";
        ArrayList<Integer> randomedNumbers = new ArrayList<>();
        ArrayList<String> lineList = new ArrayList<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new File(themeUrl));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int count = 0;
        int round = 1;
        int lineNo;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            lineList.add(line);
            count++;
//            System.out.println(line);
        }
        if (count < playerNo + 1) {
            JOptionPane.showMessageDialog(null,"This theme does not have enough questions ","warning",JOptionPane.WARNING_MESSAGE);
            isValidTheme=false;
        }
        if (isValidTheme) {
            for (int i = 0; i < playerNo + 1 ; i++) {
                Random random = new Random();
                do {
                    lineNo = random.nextInt(count);
                } while (randomedNumbers.contains(lineNo));
                randomedNumbers.add(lineNo);
                String toSplit = lineList.get(lineNo);
                String[] splitted = toSplit.split("/");
//            for (int j = 0;j<splitted.length;j++){
//                System.out.println(splitted[j]);
//            }
                Puzzle puzzle = new Puzzle(splitted[0], splitted[1], round);
                puzzleList.add(puzzle);
//            System.out.println("Controller question: "+puzzle.getQuestion());
//            System.out.println("Controller phrase: "+puzzle.getPhrase());
                round++;
            }
        }
        else
        {
            GameFrame.mainPanel.showPanel(MainPanel.TAG_START);
        }

    }

    public ArrayList<Puzzle> getPuzzleList() {
        return puzzleList;
    }
}
