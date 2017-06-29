package srs.model;

public class PlanOfStudy {
	private double neededCredits;

	public double getNeededCredits() {
		return neededCredits;
	}

	public PlanOfStudy(String semester, double neededCredits) {
		super();
		this.neededCredits = neededCredits;
	}

	public void setNeededCredits(double neededCredits) {
		this.neededCredits = neededCredits;
	}

	public Boolean verfifyPlan(Student student){
		Transcript transcript = student.getTranscript();
		double totalCredits = 0;
		for(TranscriptEntry transcriptEntry : transcript.getTranscriptEntries()){
			if(TranscriptEntry.passingGrade(transcriptEntry.getGrade())){
				totalCredits += transcriptEntry.getSection().getRepresentedCourse().getCredits();
			}		
		}
		if(totalCredits > neededCredits){
			return true;
		}else{
			return false;
		}		
	}
	
	

}
