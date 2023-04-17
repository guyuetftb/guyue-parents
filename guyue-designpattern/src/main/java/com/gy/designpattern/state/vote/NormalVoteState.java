package com.gy.designpattern.state.vote;

/**
 * @ClassName vote
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 14:53
 */
public class NormalVoteState implements VoteState {

	public void vote(String user, String voteItem, VoteManager voteManager) {
		voteManager.getVoteResult().put(user, voteItem);
		System.out.println(" 投票 成功");
	}
}
