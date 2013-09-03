package organisms.g2;

import java.util.*;
import java.io.*;
import java.awt.Color;

import organisms.*;

public final class Group2Player implements Player {

	static final String _CNAME = "Group 2";
	static final Color _CColor = new Color(1.0f, 0.67f, 0.67f);
	static final int _MAX_TURNS_SINCE_NEIGHBOR = 10;
	private int state;
	private int turnsSinceNeighbor;
	private Random rand;
	private OrganismsGame game;


	/*
	 * This method is called when the Organism is created.
	 * The key is the value that is passed to this organism by its parent (not used here)
	 */
	public void register(OrganismsGame game, int key) throws Exception
	{
		rand = new Random();
		state = rand.nextInt(256);
		this.game = game;
		this.turnsSinceNeighbor = 0;
	}

	/*
	 * Return the name to be displayed in the simulator.
	 */
	public String name() throws Exception {
		return _CNAME;
	}

	/*
	 * Return the color to be displayed in the simulator.
	 */
	public Color color() throws Exception {
		return _CColor;
	}

	/*
	 * Not, uh, really sure what this is...
	 */
	public boolean interactive() throws Exception {
		return false;
	}

	/*
	 * This is the state to be displayed to other nearby organisms
	 */
	public int externalState() throws Exception {
		return state;
	}

	/*
	 * This is called by the simulator to determine how this Organism should move.
	 * foodpresent is a four-element array that indicates whether any food is in adjacent squares
	 * neighbors is a four-element array that holds the externalState for any organism in an adjacent square
	 * foodleft is how much food is left on the current square
	 * energyleft is this organism's remaining energy
	 */
	public Move move(boolean[] foodpresent, int[] neighbors, int foodleft, int energyleft) throws Exception {
		if (neighbors[NORTH] == -1 &&
			neighbors[SOUTH] == -1 &&
			neighbors[EAST] == -1 &&
			neighbors[WEST] == -1) {
			this.turnsSinceNeighbor ++;
		}	
		
		else this.turnsSinceNeighbor = 0;
		Move m = null; // placeholder for return value
		
		if (energyleft <= game.v() * 3) {
			for (int i = 1; i < 5; i ++) {
				if (foodpresent[i] == true && neighbors[i] == -1) {
					m = new Move(i);
					break;
				}
			}
		}

		else if (game.u() * 2 < energyleft && turnsSinceNeighbor > _MAX_TURNS_SINCE_NEIGHBOR) {
			if (neighbors[SOUTH] == -1) {
				m = new Move(REPRODUCE, SOUTH,state);	
			}
		}
		
		else if (neighbors[WEST] != -1 && neighbors[EAST] != -1) {
			m = new Move(SOUTH);
		}
		
		else if (neighbors[WEST] == -1) {
			m = new Move(WEST);
		}

		if (m == null) {
			m = new Move(STAYPUT);
		}
		
		return m;
	}

}
