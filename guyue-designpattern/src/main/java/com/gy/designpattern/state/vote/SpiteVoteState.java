package com.gy.designpattern.state.vote;

/**
 * @ClassName vote
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 14:53
 */
public class SpiteVoteState implements VoteState {

	public void vote(String user, String voteItem, VoteManager voteManager) {
		System.out.println(" 取消投票资格");

		String s = voteManager.getVoteResult().get(user);
		if (s != null) {
			voteManager.getVoteResult().remove(user);
		}

	}
}
