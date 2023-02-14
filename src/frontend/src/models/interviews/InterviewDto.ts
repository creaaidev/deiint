export default class InterviewDto {
  // NOT optional id field
  id?: number;
  callId?: number;
  roomId?: number;
  candidateId?: number;
  status?: string;
  // Fields to replace the ids with names
  callName?: string;
  roomName?: string;
  candidateName?: string;

  constructor(jsonObj: Partial<InterviewDto>) {
    Object.assign(this, jsonObj);
  }
}