/*
 * ===========================================================
 * GTNA : Graph-Theoretic Network Analyzer
 * ===========================================================
 * 
 * (C) Copyright 2009-2011, by Benjamin Schiller (P2P, TU Darmstadt)
 * and Contributors
 * 
 * Project Info:  http://www.p2p.tu-darmstadt.de/research/gtna/
 * 
 * GTNA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * GTNA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 * ---------------------------------------
 * NodeImpl.java
 * ---------------------------------------
 * (C) Copyright 2009-2011, by Benjamin Schiller (P2P, TU Darmstadt)
 * and Contributors 
 * 
 * Original Author: Benjamin Schiller;
 * Contributors:    -;
 * 
 * Changes since 2011-05-17
 * ---------------------------------------
 */
package gtna.graph;

public class Node {
	private Graph graph;

	private int index;

	private int[] incomingEdges;

	private int[] outgoingEdges;

	public Node(int index, Graph graph) {
		this.index = index;
		this.graph = graph;
		this.incomingEdges = new int[] {};
		this.outgoingEdges = new int[] {};
	}

	public Node(int index, Graph graph, int incomingEdges, int outgoingEdges) {
		this.index = index;
		this.graph = graph;
		this.incomingEdges = new int[incomingEdges];
		this.outgoingEdges = new int[outgoingEdges];
	}

	public Node(int index, Graph graph, int[] incomingEdges, int[] outgoingEdges) {
		this.index = index;
		this.graph = graph;
		this.incomingEdges = incomingEdges;
		this.outgoingEdges = outgoingEdges;
	}

	public static Node[] init(int n, Graph graph) {
		Node[] nodes = new Node[n];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new Node(i, graph);
		}
		return nodes;
	}

	public String toString() {
		return this.index + "  (" + this.incomingEdges.length + " / "
				+ this.outgoingEdges.length + ")";
	}

	public int getInDegree() {
		return this.incomingEdges.length;
	}

	public int getOutDegree() {
		return this.outgoingEdges.length;
	}

	public int getDegree() {
		return this.incomingEdges.length + this.outgoingEdges.length;
	}

	/**
	 * @return the graph
	 */
	public Graph getGraph() {
		return this.graph;
	}

	/**
	 * @param graph
	 *            the graph to set
	 */
	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the incomingEdges
	 */
	public int[] getIncomingEdges() {
		return this.incomingEdges;
	}

	/**
	 * @param incomingEdges
	 *            the incomingEdges to set
	 */
	public void setIncomingEdges(int[] incomingEdges) {
		this.incomingEdges = incomingEdges;
	}

	/**
	 * @return the outgoingEdges
	 */
	public int[] getOutgoingEdges() {
		return this.outgoingEdges;
	}

	/**
	 * @param outgoingEdges
	 *            the outgoingEdges to set
	 */
	public void setOutgoingEdges(int[] outgoingEdges) {
		this.outgoingEdges = outgoingEdges;
	}
	
	public int[] getEdges() {
		int[] temp = new int[this.incomingEdges.length + this.outgoingEdges.length];
		System.arraycopy(this.incomingEdges, 0, temp, 0, this.incomingEdges.length);
		System.arraycopy(this.outgoingEdges, 0, temp, this.incomingEdges.length, this.outgoingEdges.length);
		return temp;
	}

	// TODO hasOut, hasIn, addIn, addOut, removeIn, removeOut
}
