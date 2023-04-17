package com.gy.designpattern.state.vote2;

/**
 * @ClassName vote
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 14:53
 */
public class NormalVoteState2 implements VoteState2 {

	public void vote(String user, String voteItem, VoteManager2 voteManager) {
		voteManager.getVoteResult().put(user, voteItem);
		System.out.println(" 投票 成功");
	}
}
