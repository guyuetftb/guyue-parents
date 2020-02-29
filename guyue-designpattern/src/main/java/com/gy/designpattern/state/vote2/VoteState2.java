package com.gy.designpattern.state.vote2;

/**
 * @ClassName vote
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 14:53
 */
public interface VoteState2 {

	public void vote(String user, String voteItem, VoteManager2 voteManager);
}
