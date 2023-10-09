export class FileUpload {
  id: number;
  file: File;

  constructor(id: number, file: File) {
    this.id = id;
    this.file = file;
  }
}
