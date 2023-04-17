package com.gy.designpattern.state.vote;

/**
 * @ClassName vote
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 14:53
 */
public class BlackVoteState implements VoteState {

	public void vote(String user, String voteItem, VoteManager voteManager) {
		System.out.println(" 您已经进入系统黑名单了. ");
	}
}
