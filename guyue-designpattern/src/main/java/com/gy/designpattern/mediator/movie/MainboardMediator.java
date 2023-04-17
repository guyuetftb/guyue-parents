package com.gy.designpattern.mediator.movie;

/**
 * @ClassName MainboardMediator
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-20 00:49
 */
public class MainboardMediator implements Mediator {

	private CDDriver cdDriver;
	private CPU cpu;
	private SoundCard soundCard;
	private VideoCard videoCard;

	public void setCdDriver(CDDriver cdDriver) {
		this.cdDriver = cdDriver;
	}

	public void setCpu(CPU cpu) {
		this.cpu = cpu;
	}

	public void setSoundCard(SoundCard soundCard) {
		this.soundCard = soundCard;
	}

	public void setVideoCard(VideoCard videoCard) {
		this.videoCard = videoCard;
	}

	@Override
	public void changed(Colleague colleague) {
		if (colleague == cdDriver) {
			openCDDriverReadData((CDDriver) colleague);
		} else if (colleague == cpu) {
			openCPU((CPU) colleague);
		}
	}

	private void openCDDriverReadData(CDDriver cd) {
		this.cpu.executeData(cd.getData());
	}

	private void openCPU(CPU cpu) {
		String videoData = cpu.getVideoData();
		String soundData = cpu.getSoundData();
		this.videoCard.showData(videoData);
		this.soundCard.soundData(soundData);
	}

}
