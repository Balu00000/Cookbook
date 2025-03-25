declare module 'sha.js' {
    export default class SHA {
      constructor(algorithm: string);
      update(data: string | Buffer): this;
      digest(encoding: 'hex' | 'binary' | 'base64'): string;
    }
  }
  