package parttern.gcz;

public class Mb1Observer implements Observer{
	 
	private Templater tmp;
	
	public Mb1Observer(Subject subject) {
		subject.add(this);
	}
	
	@Override
	public void update(Templater tmp) {
		this.tmp = tmp;
		System.out.println("空气："+this.tmp.getKq() +",温度："+this.tmp.getWd()+",质量："+this.tmp.getZl());
	}

	public Templater getTmp() {
		return tmp;
	}

	public void setTmp(Templater tmp) {
		this.tmp = tmp;
	}

}
