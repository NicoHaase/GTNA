/* ===========================================================
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
 * PETTest.java
 * ---------------------------------------
 * (C) Copyright 2009-2011, by Benjamin Schiller (P2P, TU Darmstadt)
 * and Contributors 
 *
 * Original Author: benni;
 * Contributors:    -;
 *
 * Changes since 2011-05-17
 * ---------------------------------------
 *
 */
package gtna.projects;

import gtna.graph.Graph;
import gtna.io.GraphReader;
import gtna.networks.Network;
import gtna.networks.model.smallWorld.ScaleFreeUndirected;
import gtna.projects.PET.cutoffType;
import gtna.routing.RoutingAlgorithm;
import gtna.util.Config;

import java.io.File;
import java.util.HashMap;

/**
 * @author benni
 * 
 */
public class PETTest {
	public static void whatToDo(int[] Nodes, double[] Alpha, int[] C,
			RoutingAlgorithm[] R, cutoffType type,
			HashMap<Integer, Integer> times) {
		System.out.println(Config.get("METRICS"));
		System.out.println(PET.minLD ? "* minLD" : "* regularLD");
		if(PET.checkGraphs){
			System.out.println("- checkGraphs");
			return;
		}
		if (PET.generateData) {
			System.out.println("- data");
		}
		if (PET.generateGraphs) {
			System.out.println("- graphs");
		}
		if (PET.plotMulti) {
			System.out.println("- plotMulti");
		}
		if (PET.plotSingle) {
			System.out.println("- plotSingle");
		}
		if (PET.plotSingleCombined) {
			System.out.println("- plotSingleCombined");
		}
		System.out.println("\n\n");
		for (int nodes : Nodes) {
			for (double alpha : Alpha) {
				int cut = PET.cutoff(nodes, type);
				Network nwLD = new ScaleFreeUndirected(nodes, alpha, -1, cut,
						null, null);
				System.out.println(times.get(nodes) + " x graphLD: "
						+ PET.graphLDFilename(nwLD, 0));
				for (int c : C) {
					Network nw = new ScaleFreeUndirected(nodes, alpha, c, cut,
							null, null);
					System.out.println();
					System.out.println(times.get(nodes) + " x "
							+ nw.description());
					System.out.println("    graph:   "
							+ PET.graphFilename(nw, 0));
					for (RoutingAlgorithm r : R) {
						Network nwr = new ScaleFreeUndirected(nodes, alpha, c,
								cut, r, null);
						System.out.println("    data:    "
								+ Config.get("MAIN_DATA_FOLDER") + nwr.nodes()
								+ "/" + nwr.folder());
					}
				}
				System.out.println("\n\n");
			}
		}
	}

	public static void checkGraphs(int[] Nodes, double[] Alpha, int[] C,
			cutoffType type, HashMap<Integer, Integer> Times) {
		for (int nodes : Nodes) {
			int cut = PET.cutoff(nodes, type);
			int times = Times.get(nodes);
			for (double alpha : Alpha) {
				for (int i = 0; i < times; i++) {
					Network nwLD = new ScaleFreeUndirected(nodes, alpha, -1,
							cut, null, null);
					String filenameLD = PET.graphLDFilename(nwLD, i);
					if (!(new File(filenameLD)).exists()) {
						System.out
								.println("ERROR LD - does not exist: " + filenameLD);
						continue;
					}
					Graph g = GraphReader.read(filenameLD);
					if (g == null) {
						System.out
								.println("ERROR LD - could not read: " + filenameLD);
					} else {
						System.out.println("LD - read: " + filenameLD);
					}
				}
				System.out.println("");
				for (int c : C) {
					for (int i = 0; i < times; i++) {
						Network nw = new ScaleFreeUndirected(nodes, alpha, c,
								cut, null, null);
						String filename = PET.graphFilename(nw, i);
						if (!(new File(filename)).exists()) {
							System.out.println("ERROR: does not exist: " + filename);
							continue;
						}
						Graph g = GraphReader.read(filename);
						if (g == null) {
							System.out.println("ERROR: could not read: " + filename);
						} else {
							System.out.println("read: " + filename);
						}
					}
				}
				System.out.println("\n\n");
			}
		}
	}
}
