<template>
    <v-card flat>
      <h2>Entrevistas</h2>
      <v-text-field
        v-model="search"
        append-inner-icon="fas fa-search"
        label="Pesquisar"
        single-line
        hide-details
      ></v-text-field>
      <v-data-table
        :headers="headers"
        :items="interviews"
        :search="search"
        multi-sort
        no-data-text="Nenhuma entrevista a apresentar."
      > <!-- If I find a way to leave all groups open by default I'll add groupBy -->
        <template v-slot:item="{ item }">
          <tr>
            <td style="padding-left: 1%" >{{ item.raw.id }}</td>
            <td>{{ item.raw.callName }}</td>
            <td>{{ item.raw.roomName }}</td>
            <td>{{ item.raw.candidateName }}</td>
            <td>{{ item.raw.status }}</td>
            <td>
              <v-btn
                color="secondary"
                @click="inspectInterview(item.raw.id)"
                style="margin: 0px 5px 0px 5px;"
              >
                <v-icon>fas fa-eye</v-icon>
              </v-btn>
              <v-btn
                color="primary"
                @click="editInterview(item.raw.id)"
                :disabled="item.raw.status == 'CANCELADA' || item.raw.status == 'TERMINADA'"
                style="margin: 0px 5px 0px 5px;"
              >
                <v-icon>fas fa-cog</v-icon>
              </v-btn>
              <v-btn
                color="error"
                @click="deleteInterview(item.raw.id)"
                style="margin: 0px 5px 0px 5px;"
              >
                <v-icon>fas fa-trash</v-icon>
              </v-btn>
            </td>
          </tr>
        </template>
      </v-data-table>
    </v-card>
</template>
  
<script setup lang="ts">
  import type InterviewDto from '@/models/interviews/InterviewDto';
  import type CallDto from '@/models/calls/CallDto';
  import type RoomDto from '@/models/rooms/RoomDto';
  import type CandidateDto from '@/models/candidates/CandidateDto';
  import RemoteServices from '@/services/RemoteServices';
  import { reactive, ref } from 'vue';

  import { useRouter } from 'vue-router';
  
  let search = ref('');
  // const groupBy = [ { title: 'Concurso', key: 'callName', value: 'callName' } ];
  const headers = [
    { title: 'ID', value: 'id', key: 'id', sortable: true, filterable: false },
    { title: 'Nome do Concurso', value: 'callName', key: 'callName', sortable: true, filterable: true },
    { title: 'Sala', value: 'roomName', key: 'roomName', sortable: true, filterable: true},
    { title: 'Candidato', value: 'candidateName', key: 'candidateName', sortable: true, filterable: true },
    { title: 'Estado', value: 'status', key: 'status', sortable: true, filterable: true },
    { title: 'Ações', value: 'actions', sortable: false, filterable: false }
  ];
  
  let interviews: InterviewDto[] = reactive([]);

  let calls: CallDto[] = reactive([]);
  let rooms: RoomDto[] = reactive([]);
  let candidates: CandidateDto[] = reactive([]);
  
  RemoteServices.getCalls().then((data) => {
    calls.push(...data);
  });
  RemoteServices.getRooms().then((data) => {
    rooms.push(...data.filter((room) => room.available));
  });
  RemoteServices.getCandidates().then((data) => {
    candidates.push(...data);
  });

  // TODO: refactor ig
  RemoteServices.getInterviews().then((data) => {
    interviews.push(...data);

    // TODO: remove the as any?
    // TODO: consider if note worthy adding else statements to the if's
    interviews.forEach(async (interview) => {
      interview.callName = 'Pendente';
      interview.roomName = 'Pendente';
      interview.candidateName = 'Pendente';

      if (interview.callId)
        interview.callName = calls.find((call) => call.id === interview.callId)?.name as any;

      if (interview.roomId)
        interview.roomName = rooms.find((room) => room.id === interview.roomId)?.name as any;
        
      if (interview.candidateId)
        interview.candidateName = candidates.find((candidate) =>
         candidate.id === interview.candidateId)?.name as any;
    
    });
  });

  const deleteInterview = (id: number) => {
    RemoteServices.deleteInterview(id).then(() => {

      // TODO: Check if this is correct or can be done in another way
      interviews.splice(interviews.findIndex((interview) => interview.id === id), 1);

      // TODO: change to better alerts; Alert = true;
      alert('Entrevista eliminada com sucesso!');
    });
  };

  const router = useRouter();
  const editInterview = (id: number) => {
    router.push({ name: 'interviews-edit', params: { id: id } });
  };
  const inspectInterview = (id: number) => {
    router.push({ name: 'interviews-inspect', params: { id: id } });
  };

</script>
  