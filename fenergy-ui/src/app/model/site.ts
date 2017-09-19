// Tommy Li (tommy.li@firefire.co), 2017-09-04

export class Site {

  nmi: string;
  label: string;

  constructor(nmi: string, label: string) {
    this.nmi = nmi;
    this.label = label;
  }

  get displayLabel(): string {
    return this.label != null ? this.label : this.nmi;
  }
}
