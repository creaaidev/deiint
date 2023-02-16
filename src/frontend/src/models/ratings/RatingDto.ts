export default class RatingDto {
  id?: number;
  interviewId?: number;
  examinerName?: string;
  // Various scores
  ss?: number;
  rp?: number;
  lin?: number;
  win?: number;
  hw?: number;

  constructor(jsonObj: Partial<RatingDto>) {
    Object.assign(this, jsonObj);
  }
}