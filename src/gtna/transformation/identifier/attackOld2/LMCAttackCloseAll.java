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
 * LocalMCAttackCloseAll.java
 * ---------------------------------------
 * (C) Copyright 2009-2011, by Benjamin Schiller (P2P, TU Darmstadt)
 * and Contributors 
 *
 * Original Author: "Benjamin Schiller";
 * Contributors:    -;
 *
 * Changes since 2011-05-17
 * ---------------------------------------
 *
 */
package gtna.transformation.identifier.attackOld2;

import gtna.graph.NodeImpl;
import gtna.routing.node.RingNode;
import gtna.transformation.Transformation;

import java.util.Random;

public class LMCAttackCloseAll extends LMCAttack implements Transformation {
	boolean[] attacks;
	int numAtt;
	Random rand = new Random();

	public LMCAttackCloseAll(int maxIter, int mode, double pRand, double delta,
			int C, boolean deg1, String file, int number) {
		super("LMC_ATTACK_CLOSE_ALL", maxIter, mode, pRand, delta, C, deg1,
				file, number);
		numAtt = number;

	}

	@Override
	public void determineAttacker(NodeImpl[] curNodes, Random rand) {
		attacks = new boolean[curNodes.length];
		int count = 0;
		while (count < numAtt) {
			int next = rand.nextInt(curNodes.length);
			if (!attacks[next]) {
				attacks[next] = true;
				count++;
			}
		}
	}

	@Override
	public boolean isAttacker(RingNode node) {
		return attacks[node.index()];
	}

	@Override
	public void lauchAttackActive(RingNode node, Random rand) {
		// TODO Auto-generated method stub

	}

	@Override
	public double lauchAttackPassiv(RingNode node, RingNode cur, double min,
			Random rand) {
		double res;
		if (this.modus == 2 || modus == 3) {
			res = cur.getID().pos + min + min * rand.nextDouble();
		} else {
			res = cur.getID().pos + min * rand.nextDouble();
		}
		if (res > 1) {
			res = res - 1;
		}
		return res;
	}
}