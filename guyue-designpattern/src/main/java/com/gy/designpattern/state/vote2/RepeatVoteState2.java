package com.gy.designpattern.state.vote2;

import com.gy.designpattern.state.vote.VoteState;

/**
 * @ClassName vote
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 14:53
 */
public class RepeatVoteState2 implements VoteState2 {

	public void vote(String user, String voteItem, VoteManager2 voteManager) {
		System.out.println(" 投票 重复");

		if (voteManager.getVoteCount().get(user) >= 4) {
			voteManager.getMapState().put(user, new SpiteVoteState2());
		}
	}
}
