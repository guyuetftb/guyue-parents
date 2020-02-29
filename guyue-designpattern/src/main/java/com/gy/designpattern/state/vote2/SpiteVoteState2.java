package com.gy.designpattern.state.vote2;

import com.gy.designpattern.state.vote.VoteState;

/**
 * @ClassName vote
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 14:53
 */
public class SpiteVoteState2 implements VoteState2 {

	public void vote(String user, String voteItem, VoteManager2 voteManager) {
		System.out.println(" 取消投票资格");

		String s = voteManager.getVoteResult().get(user);
		if (s != null) {
			voteManager.getVoteResult().remove(user);
		}

		if (voteManager.getVoteCount().get(user) >= 7) {
			voteManager.getMapState().put(user, new BlackVoteState2());
		}

	}
}
