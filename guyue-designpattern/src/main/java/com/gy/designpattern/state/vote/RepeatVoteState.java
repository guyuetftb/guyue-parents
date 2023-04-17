package com.gy.designpattern.state.vote;

/**
 * @ClassName vote
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 14:53
 */
public class RepeatVoteState implements VoteState {

	public void vote(String user, String voteItem, VoteManager voteManager) {
		System.out.println(" 投票 重复");
	}
}
