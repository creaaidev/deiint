import axios from 'axios';
import type { AxiosResponse } from 'axios';
import { useAppearanceStore } from '@/stores/appearance';
import DeiintError from '@/models/error/DeiintError';
import type CallDto from '@/models/calls/CallDto';

const httpClient = axios.create();
httpClient.defaults.timeout = 50000;
httpClient.defaults.baseURL = import.meta.env.VITE_ROOT_API;
httpClient.defaults.headers.post['Content-Type'] = 'application/json';

export default class RemoteServices {
  static async getCalls(): Promise<CallDto[]> {
    return httpClient.get('/calls');
  }

  static async createCall(call: CallDto): Promise<CallDto> {
    return httpClient.post('/calls', call);
  }

  static async getCall(callId: number): Promise<CallDto> {
    return httpClient.get(`/calls/${callId}`);
  }

  static async updateCall(call: CallDto): Promise<CallDto> {
    return httpClient.put(`/calls/${call.id}`, call);
  }

  static async deleteCall(callId: number): Promise<AxiosResponse> {
    return httpClient.delete(`/calls/${callId}`);
  }

  static async errorMessage(error: any): Promise<string> {
    if (error.message === 'Network Error') {
      return 'Unable to connect to the server';
    } else if (error.message.split(' ')[0] === 'timeout') {
      return 'Request timeout - Server took too long to respond';
    } else {
      return error.response?.data?.message ?? 'Unknown Error';
    }
  }

  static async handleError(error: any): Promise<never> {
    const deiintErr = new DeiintError(
      await RemoteServices.errorMessage(error),
      error.response?.data?.code ?? -1
    );
    useAppearanceStore().pushError(deiintErr);
    throw deiintErr;
  }
}

httpClient.interceptors.request.use(
  (request) => request,
  RemoteServices.handleError
);
httpClient.interceptors.response.use(
  (response) => response.data,
  RemoteServices.handleError
);
