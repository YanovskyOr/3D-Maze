package view;

import java.util.Observable;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;

public class MyView extends Observable implements View, Observer {

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyMazeIsReady(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayMaze(Maze3d maze) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayMessage(String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

}
