import {ExtFile} from "./ext.file";

export class Product {
  id: String = '';
  title: string = '';
  price: number = 0;
  files: ExtFile[] = [];

  constructor() {
  }

}

export class PageResponse {
  content: Product[] = [];
  totalPages: number = 0;
  number: number = 0;

  constructor() {
  }
}

export class PageRequest {
  page: number = 0;
  size: number = 0;

  constructor() {
  }
}
