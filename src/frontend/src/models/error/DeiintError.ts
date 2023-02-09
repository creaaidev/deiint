export default class DeiintError extends Error {
  public code?: number;

  constructor(m: string, c?: number) {
    super(m);
    if (c != undefined) this.code = c;
  }
}
