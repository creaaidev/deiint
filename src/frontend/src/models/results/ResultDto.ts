export default class ResultDto {
  id?: number;
  candidateId?: number;
  call?: string;
  // List of ratings
  ratings?: {
    ss?: number;
    rp?: number;
    lin?: number;
    win?: number;
    hw?: number;
  };

  constructor(jsonObj: Partial<ResultDto>) {
    Object.assign(this, jsonObj);
  }
}