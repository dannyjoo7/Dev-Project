import { useState, useEffect } from 'react';
import { Plus, Trash2, Edit2, ArrowUp, ArrowDown } from 'lucide-react';
import { Button } from './ui/button';
import { Input } from './ui/input';
import { Card } from './ui/card';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from './ui/select';
import { Label } from './ui/label';
import type { Message, Participant } from './SequenceDiagram';

interface MessageEditorProps {
  participants: Participant[];
  messages: Message[];
  onAdd: (message: Omit<Message, 'id'>) => void;
  onRemove: (id: string) => void;
  onUpdate: (id: string, message: Omit<Message, 'id'>) => void;
  onMoveUp: (id: string) => void;
  onMoveDown: (id: string) => void;
  selectedMessage?: Message;
}

export function MessageEditor({ 
  participants, 
  messages, 
  onAdd, 
  onRemove, 
  onUpdate,
  onMoveUp,
  onMoveDown,
  selectedMessage 
}: MessageEditorProps) {
  const [from, setFrom] = useState('');
  const [to, setTo] = useState('');
  const [text, setText] = useState('');
  const [type, setType] = useState<Message['type']>('sync');
  const [editingId, setEditingId] = useState<string | null>(null);

  useEffect(() => {
    if (selectedMessage) {
      setFrom(selectedMessage.from);
      setTo(selectedMessage.to);
      setText(selectedMessage.text);
      setType(selectedMessage.type);
      setEditingId(selectedMessage.id);
    }
  }, [selectedMessage]);

  const handleSubmit = () => {
    if (!from || !to || !text.trim()) return;

    const messageData = { from, to, text: text.trim(), type };

    if (editingId) {
      onUpdate(editingId, messageData);
      setEditingId(null);
    } else {
      onAdd(messageData);
    }

    resetForm();
  };

  const resetForm = () => {
    setFrom('');
    setTo('');
    setText('');
    setType('sync');
    setEditingId(null);
  };

  const handleEdit = (message: Message) => {
    setFrom(message.from);
    setTo(message.to);
    setText(message.text);
    setType(message.type);
    setEditingId(message.id);
  };

  const getMessageTypeLabel = (type: Message['type']) => {
    switch (type) {
      case 'sync': return '동기 호출';
      case 'async': return '비동기 호출';
      case 'return': return '응답';
      case 'self': return '자기 호출';
    }
  };

  return (
    <Card className="p-4">
      <h3 className="font-semibold mb-4 text-slate-900">
        {editingId ? '메시지 편집' : '메시지 추가'}
      </h3>

      <div className="space-y-3 mb-4">
        <div>
          <Label htmlFor="from">발신자</Label>
          <Select value={from} onValueChange={setFrom}>
            <SelectTrigger id="from">
              <SelectValue placeholder="선택" />
            </SelectTrigger>
            <SelectContent>
              {participants.map((p) => (
                <SelectItem key={p.id} value={p.id}>
                  {p.name}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>
        </div>

        <div>
          <Label htmlFor="to">수신자</Label>
          <Select value={to} onValueChange={setTo}>
            <SelectTrigger id="to">
              <SelectValue placeholder="선택" />
            </SelectTrigger>
            <SelectContent>
              {participants.map((p) => (
                <SelectItem key={p.id} value={p.id}>
                  {p.name}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>
        </div>

        <div>
          <Label htmlFor="message-type">메시지 유형</Label>
          <Select value={type} onValueChange={(v) => setType(v as Message['type'])}>
            <SelectTrigger id="message-type">
              <SelectValue />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="sync">동기 호출 (→)</SelectItem>
              <SelectItem value="async">비동기 호출 (⇢)</SelectItem>
              <SelectItem value="return">응답 (←)</SelectItem>
              <SelectItem value="self">자기 호출 (↻)</SelectItem>
            </SelectContent>
          </Select>
        </div>

        <div>
          <Label htmlFor="text">메시지</Label>
          <Input
            id="text"
            value={text}
            onChange={(e) => setText(e.target.value)}
            placeholder="메시지 내용"
            onKeyPress={(e) => e.key === 'Enter' && handleSubmit()}
          />
        </div>

        <div className="flex gap-2">
          <Button onClick={handleSubmit} className="flex-1">
            <Plus className="h-4 w-4 mr-1" />
            {editingId ? '수정' : '추가'}
          </Button>
          {editingId && (
            <Button variant="outline" onClick={resetForm}>
              취소
            </Button>
          )}
        </div>
      </div>

      <div className="border-t pt-4">
        <h4 className="text-sm font-medium mb-3 text-slate-700">메시지 목록</h4>
        <div className="space-y-2 max-h-[400px] overflow-y-auto">
          {messages.map((message, index) => {
            const fromParticipant = participants.find(p => p.id === message.from);
            const toParticipant = participants.find(p => p.id === message.to);
            
            return (
              <div
                key={message.id}
                className="flex items-start gap-2 text-sm"
              >
                <div className="flex-1 bg-slate-50 rounded-lg border border-slate-200 p-3">
                  <div className="text-slate-900 font-medium mb-1">
                    {message.text}
                  </div>
                  <div className="text-xs text-slate-500">
                    {fromParticipant?.name} → {toParticipant?.name} 
                    <span className="ml-2 text-blue-600">
                      ({getMessageTypeLabel(message.type)})
                    </span>
                  </div>
                  <div className="flex gap-1 mt-2">
                    <Button
                      variant="ghost"
                      size="sm"
                      onClick={() => onMoveUp(message.id)}
                      disabled={index === 0}
                      className="h-7 px-2 text-xs"
                    >
                      <ArrowUp className="h-3 w-3 mr-1" />
                      위로
                    </Button>
                    <Button
                      variant="ghost"
                      size="sm"
                      onClick={() => onMoveDown(message.id)}
                      disabled={index === messages.length - 1}
                      className="h-7 px-2 text-xs"
                    >
                      <ArrowDown className="h-3 w-3 mr-1" />
                      아래로
                    </Button>
                    <Button
                      variant="ghost"
                      size="sm"
                      onClick={() => handleEdit(message)}
                      className="h-7 px-2 text-xs text-blue-600 hover:text-blue-700 hover:bg-blue-50"
                    >
                      <Edit2 className="h-3 w-3 mr-1" />
                      편집
                    </Button>
                    <Button
                      variant="ghost"
                      size="sm"
                      onClick={() => onRemove(message.id)}
                      className="h-7 px-2 text-xs text-red-600 hover:text-red-700 hover:bg-red-50"
                    >
                      <Trash2 className="h-3 w-3 mr-1" />
                      삭제
                    </Button>
                  </div>
                </div>
                <div className="flex items-center justify-center min-w-[32px] h-8 rounded-full bg-blue-600 text-white text-sm font-semibold">
                  {index + 1}
                </div>
              </div>
            );
          })}
        </div>
      </div>
    </Card>
  );
}