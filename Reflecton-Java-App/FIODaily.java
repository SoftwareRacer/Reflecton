package com.pinnovations.download;

public class FIODaily {

	FIODataBlock daily;

	public FIODaily(ForecastIO fio){

		this.daily = null;
		init(fio);

	}

	private void init(ForecastIO fio){

		if(fio.hasDaily()){
			this.daily = new FIODataBlock(fio.getDaily());
			this.daily.setTimezone(fio.getTimezone());
		}
		else {
			this.daily = null;
		}
	}

	/**
	 * Returns the data point for the given day in the daily report
	 * @param day the day to get
	 * @return return the data point with the report
	 */
	public FIODataPoint getDay(int day){

		return this.daily == null ? null : this.daily.datapoint(day);  
	}

	/**
	 * Returns the days in the daily report
	 * @return integer with the number of days. Returns -1 if there is not data in the report.
	 */
	public int days(){
		return this.daily == null ? -1 : this.daily.datablockSize();
	}

	/**
	 * Returns forecast summary
	 * @return forecast summary or null
	 */
	public String getSummary() {
		return daily == null ? null : daily.summary();
	}

	/**
	 * Returns forecast icon
	 * @return forecast icon or null
	 */
	public String getIcon() {
		return daily == null ? null : daily.icon();
	}
}
