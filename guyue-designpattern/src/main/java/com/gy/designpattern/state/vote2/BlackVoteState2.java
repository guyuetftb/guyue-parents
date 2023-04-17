package com.gy.designpattern.state.vote2;

/**
 * @ClassName vote
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 14:53
 */
public class BlackVoteState2 implements VoteState2 {

	public void vote(String user, String voteItem, VoteManager2 voteManager) {
		System.out.println(" 您已经进入系统黑名单了. ");
	}
}
