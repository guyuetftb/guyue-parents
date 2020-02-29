package com.gy.designpattern.state.vote;

/**
 * @ClassName vote
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 14:53
 */
public interface VoteState {

	public void vote(String user, String voteItem, VoteManager voteManager);
}
