package com.gy.designpattern.state.vote;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName vote
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-23 14:53
 */
public class VoteManager {

	private VoteState voteState;

	/**
	 * 每个用户的投票次数
	 */
	private Map<String, Integer> voteCount = new HashMap<String, Integer>();

	/**
	 * 每个用户的投票结果
	 */
	public Map<String, String> voteResult = new HashMap<String, String>();


	public Map<String, VoteState> mapState = new HashMap<String, VoteState>();

	public Map<String, VoteState> getMapState() {
		return mapState;
	}

	public Map<String, String> getVoteResult() {
		return voteResult;
	}


	public void vote(String user, String voteItem) {
		Integer oldValueCount = voteCount.get(user);
		if (oldValueCount == null) {
			oldValueCount = 0;
		}

		oldValueCount = oldValueCount + 1;
		voteCount.put(user, oldValueCount);

		if (oldValueCount == 1) {
			voteState = new NormalVoteState();
		} else if (oldValueCount >= 5 && oldValueCount < 8) {
			voteState = new RepeatVoteState();
		} else if (oldValueCount > 8) {
			voteState = new SpiteVoteState();
		} else {
			voteState = new BlackVoteState();
		}
		voteState.vote(user, voteItem, this);
	}
}
