export default class CallDto {
  id?: number;
  name?: string;
  interviewsPeriodOpen?: boolean;

  constructor(jsonObj: Partial<CallDto>) {
    Object.assign(this, jsonObj);
  }
}
