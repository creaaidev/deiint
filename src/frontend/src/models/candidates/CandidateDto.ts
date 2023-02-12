export default class CandidateDto {
  id?: number;
  name?: string;
  email?: string;

  constructor(jsonObj: Partial<CandidateDto>) {
    Object.assign(this, jsonObj);
  }
}