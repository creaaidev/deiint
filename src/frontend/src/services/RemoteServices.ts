import axios from 'axios';
import type { AxiosResponse } from 'axios';
import { useAppearanceStore } from '@/stores/appearance';
import DeiintError from '@/models/error/DeiintError';
import type CallDto from '@/models/calls/CallDto';
import type CandidateDto from '@/models/candidates/CandidateDto';
import type InterviewDto from '@/models/interviews/InterviewDto';
import type RoomDto from '@/models/rooms/RoomDto';

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


  // Get candidates from API server :  https://eindhoven.rnl.tecnico.ulisboa.pt/school-reg/api/v1
  static async getCandidates(): Promise<CandidateDto[]> {
    return axios.get('/api/candidates').then((response) => response.data);
  }
  // TODO: Catch exceptions?
  // { headers: { "Authorization": "Bearer ist199281"}});

  static async getCandidate(candidateId: number): Promise<CandidateDto> {
    return axios.get(`/api/candidates/${candidateId}`).then((response) => response.data);
  }

  static async createCandidate(candidate: CandidateDto): Promise<CandidateDto> {
    return httpClient.post('/candidates', candidate);
  }

  static async updateCandidate(candidate: CandidateDto): Promise<CandidateDto> {
    return httpClient.put(`/candidates/${candidate.id}`, candidate);
  }

  static async deleteCandidate(candidateId: number): Promise<AxiosResponse> {
    return httpClient.delete(`/candidates/${candidateId}`);
  }

  // Get candidate's interviews from API server
  static async getCandidateInterviews(candidateId: number): Promise<InterviewDto[]> {
    return httpClient.get(`/candidates/${candidateId}/interviews`);
  }

  // Update candidate's interviews from API server
  static async updateCandidateInterviews(candidateId: number, interviews: InterviewDto[]): Promise<InterviewDto[]> {
    return httpClient.put(`/candidates/${candidateId}/interviews`, interviews);
  }

  // Get interviews from backend
  static async getInterviews(): Promise<InterviewDto[]> {
    return httpClient.get('/interviews');
  }

  static async createInterview(interview: InterviewDto): Promise<InterviewDto> {
    return httpClient.post('/interviews', interview);
  }

  static async getInterview(interviewId: number): Promise<InterviewDto> {
    return httpClient.get(`/interviews/${interviewId}`);
  }

  static async updateInterview(interview: InterviewDto): Promise<InterviewDto> {
    return httpClient.put(`/interviews/${interview.id}`, interview);
  }

  static async deleteInterview(interviewId: number): Promise<AxiosResponse> {
    return httpClient.delete(`/interviews/${interviewId}`);
  }


  static async getRoom(roomId: number): Promise<RoomDto> {
    return axios.get(`/api/rooms/${roomId}`).then((response) => response.data);
  }

  static async getRooms(): Promise<RoomDto[]> {
    return axios.get('/api/rooms').then((response) => response.data);
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
