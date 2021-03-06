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
 * ClusteringCoefficient.java
 * ---------------------------------------
 * (C) Copyright 2009-2011, by Benjamin Schiller (P2P, TU Darmstadt)
 * and Contributors 
 * 
 * Original Author: Benjamin Schiller;
 * Contributors:    -;
 * 
 * Changes since 2011-05-17
 * ---------------------------------------
 * 2011-05-24 : Bugfix - excluding v from v's neighborhood (BS);
 * 2011-05-24 : Performance - switching from array- to set-based 
 *              computations, should be faster especially on 
 *              low-degree graphs (BS);
 * 2011-05-24 : all config parameters starting with CC (BS);
 */
package gtna.metrics.basic;

import gtna.data.Single;
import gtna.graph.Edges;
import gtna.graph.Graph;
import gtna.graph.Node;
import gtna.metrics.Metric;
import gtna.networks.Network;
import gtna.util.Util;

import java.util.HashMap;

public class ClusteringCoefficient extends Metric {
	// TODO add LCC => binning?!?
	// TODO add distribution of LCC?!?

	private double[] localClusteringCoefficient;

	private double clusteringCoefficient;

	private double transitivity;

	public ClusteringCoefficient() {
		super("CLUSTERING_COEFFICIENT");
	}

	@Override
	public boolean applicable(Graph g, Network n, HashMap<String, Metric> m) {
		return true;
	}

	@Override
	public void computeData(Graph graph, Network nw,
			HashMap<String, Metric> metrics) {
		Edges edges = new Edges(graph.getNodes(), graph.generateEdges());
		this.localClusteringCoefficient = this
				.computeLocalClusteringCoefficient(graph.getNodes(), edges);
		this.clusteringCoefficient = this
				.computeClusteringCoefficient(this.localClusteringCoefficient);
		this.transitivity = this.computeTransitivity(graph);
	}

	private double[] computeLocalClusteringCoefficient(Node[] nodes, Edges edges) {
		double[] lcc = new double[nodes.length];
		for (int i = 0; i < nodes.length; i++) {
			lcc[i] = this.computeLocalClusteringCoefficient(nodes[i], edges);
		}
		return lcc;
	}

	private double computeLocalClusteringCoefficient(Node node, Edges edges) {
		if (node.getOutDegree() <= 1) {
			return 0;
		}
		int[] neighbors = node.getOutgoingEdges();
		int counter = 0;
		for (int u : neighbors) {
			for (int v : neighbors) {
				if (u != v && edges.contains(u, v)) {
					counter++;
				}
			}
		}
		return (double) counter
				/ (double) (neighbors.length * (neighbors.length - 1));
	}

	/**
	 * transitivity = triangles/(3*triples) in graph
	 * 
	 * @param g
	 * @return
	 */
	private double computeTransitivity(Graph g) {
		int triangles = 0;
		int triples = 0;
		Node[] nodes = g.getNodes();
		for (int i = 0; i < nodes.length; i++) {
			triples = triples + (nodes[i].getOutDegree())
					* (nodes[i].getOutDegree() - 1) / 2;
			int[] out = nodes[i].getOutgoingEdges();
			for (int j = 0; j < out.length; j++) {
				for (int k = j + 1; k < out.length; k++) {
					if (nodes[out[j]].hasNeighbor(out[k])) {
						triangles++;
					}
				}
			}
		}
		return (double) triangles / (double) triples;
	}

	private double computeClusteringCoefficient(double[] lcc) {
		return Util.avg(lcc);
	}

	@Override
	public boolean writeData(String folder) {
		boolean success = true;
		return success;
	}

	@Override
	public Single[] getSingles() {
		Single clusteringCoefficient = new Single(
				"CLUSTERING_COEFFICIENT_CLUSTERING_COEFFICIENT",
				this.clusteringCoefficient);
		Single transitivity = new Single("CLUSTERING_COEFFICIENT_TRANSITIVITY",
				this.transitivity);
		return new Single[] { clusteringCoefficient, transitivity };
	}
}
