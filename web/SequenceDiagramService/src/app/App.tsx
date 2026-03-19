import { useState, useRef } from "react";
import {
  SequenceDiagram,
  type Participant,
  type Message,
} from "./components/SequenceDiagram";
import {
  PARTICIPANT_WIDTH,
  PARTICIPANT_SPACING,
  MESSAGE_HEIGHT,
  HEADER_HEIGHT,
} from "./components/constants";

import { ParticipantEditor } from "./components/ParticipantEditor";
import { MessageEditor } from "./components/MessageEditor";
import { Button } from "./components/ui/button";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "./components/ui/select";
import {
  Sheet,
  SheetContent,
  SheetTrigger,
  SheetTitle,
  SheetDescription,
} from "./components/ui/sheet";
import {
  Download,
  Upload,
  Trash2,
  Menu,
  Image,
} from "lucide-react";
import { useLanguage } from "./contexts/LanguageContext";
import { getTranslation, type Language } from "../utils/translations";

export default function App() {
  const { language, setLanguage } = useLanguage();
  const t = (key: any) => getTranslation(language, key);

  const handleLanguageChange = (value: string) => {
    setLanguage(value as Language);
  };

  const [participants, setParticipants] = useState<
    Participant[]
  >([
    { id: "1", name: t("client"), icon: "user" },
    { id: "2", name: t("server"), icon: "server" },
    { id: "3", name: t("database"), icon: "database" },
  ]);

  const handleReorderParticipants = (
    newParticipants: Participant[],
  ) => {
    setParticipants(newParticipants);
  };

  const [messages, setMessages] = useState<Message[]>([
    {
      id: "1",
      from: "1",
      to: "2",
      text: t("sendRequest"),
      type: "sync",
    },
    {
      id: "2",
      from: "2",
      to: "3",
      text: t("queryData"),
      type: "sync",
    },
    {
      id: "3",
      from: "3",
      to: "2",
      text: t("returnData"),
      type: "return",
    },
    {
      id: "4",
      from: "2",
      to: "1",
      text: t("sendResponse"),
      type: "return",
    },
  ]);

  const [selectedMessage, setSelectedMessage] = useState<
    Message | undefined
  >();

  const addParticipant = (name: string, icon?: string) => {
    const newParticipant: Participant = {
      id: Date.now().toString(),
      name,
      icon,
    };
    setParticipants([...participants, newParticipant]);
  };

  const removeParticipant = (id: string) => {
    setParticipants(participants.filter((p) => p.id !== id));
    // Remove related messages
    setMessages(
      messages.filter((m) => m.from !== id && m.to !== id),
    );
  };

  const updateParticipantIcon = (id: string, icon?: string) => {
    setParticipants(
      participants.map((p) =>
        p.id === id ? { ...p, icon } : p,
      ),
    );
  };

  const updateParticipantName = (id: string, name: string) => {
    setParticipants(
      participants.map((p) =>
        p.id === id ? { ...p, name } : p,
      ),
    );
  };

  const addMessage = (message: Omit<Message, "id">) => {
    const newMessage: Message = {
      ...message,
      id: Date.now().toString(),
    };
    setMessages([...messages, newMessage]);
  };

  const updateMessage = (
    id: string,
    message: Omit<Message, "id">,
  ) => {
    setMessages(
      messages.map((m) =>
        m.id === id ? { ...message, id } : m,
      ),
    );
  };

  const removeMessage = (id: string) => {
    setMessages(messages.filter((m) => m.id !== id));
  };

  const moveMessageUp = (id: string) => {
    const index = messages.findIndex((m) => m.id === id);
    if (index > 0) {
      const newMessages = [...messages];
      [newMessages[index - 1], newMessages[index]] = [
        newMessages[index],
        newMessages[index - 1],
      ];
      setMessages(newMessages);
    }
  };

  const moveMessageDown = (id: string) => {
    const index = messages.findIndex((m) => m.id === id);
    if (index < messages.length - 1) {
      const newMessages = [...messages];
      [newMessages[index], newMessages[index + 1]] = [
        newMessages[index + 1],
        newMessages[index],
      ];
      setMessages(newMessages);
    }
  };

  const exportDiagram = () => {
    const data = {
      participants,
      messages,
    };
    const blob = new Blob([JSON.stringify(data, null, 2)], {
      type: "application/json",
    });
    const url = URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = "sequence-diagram.json";
    a.click();
    URL.revokeObjectURL(url);
  };

  const importDiagram = () => {
    const input = document.createElement("input");
    input.type = "file";
    input.accept = "application/json";
    input.onchange = (e) => {
      const file = (e.target as HTMLInputElement).files?.[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          try {
            const data = JSON.parse(e.target?.result as string);
            if (data.participants && data.messages) {
              setParticipants(data.participants);
              setMessages(data.messages);
            }
          } catch (error) {
            console.error("Failed to import diagram:", error);
            alert(t("importError"));
          }
        };
        reader.readAsText(file);
      }
    };
    input.click();
  };

  const clearAll = () => {
    if (confirm(t("confirmClearAll"))) {
      setParticipants([]);
      setMessages([]);
    }
  };

  const diagramRef = useRef<HTMLDivElement>(null);
  const svgRef = useRef<SVGSVGElement>(null);

  const exportSVG = () => {
    const svg = svgRef.current;
    if (!svg) return;

    const svgData = new XMLSerializer().serializeToString(svg);
    const blob = new Blob([svgData], {
      type: "image/svg+xml;charset=utf-8",
    });
    const url = URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = "sequence-diagram.svg";
    a.click();
    URL.revokeObjectURL(url);
  };

  const exportPNG = () => {
    // 1. svgRef.current에서 SVG 노드를 가져옵니다.
    const svgNode = svgRef.current;
    if (!svgNode) return;

    // 1. 진짜 필요한 폭만 계산 (여백 제외)
    const realWidth =
      participants.length *
      (PARTICIPANT_WIDTH + PARTICIPANT_SPACING);

    const totalHeight =
      HEADER_HEIGHT + messages.length * MESSAGE_HEIGHT + 100;

    // 2. SVG 전체를 복제
    const clonedSvg = svgNode.cloneNode(true) as SVGSVGElement;

    // 3. 복제된 SVG의 폭을 진짜 필요한 폭으로 설정
    clonedSvg.setAttribute("width", realWidth.toString());
    clonedSvg.setAttribute(
      "viewBox",
      `0 0 ${realWidth} ${totalHeight}`,
    );

    const serializer = new XMLSerializer();
    let svgString = serializer.serializeToString(clonedSvg);

    const img = new window.Image();
    img.crossOrigin = "anonymous";

    img.onload = () => {
      const canvas = document.createElement("canvas");

      // 4. Canvas 크기도 여백 없는 폭으로 설정
      canvas.width = realWidth;
      canvas.height = totalHeight;

      const ctx = canvas.getContext("2d");

      // 5. 배경색 채우기 (복제된 SVG가 투명배경일 경우를 대비)
      ctx!.fillStyle = "white";
      ctx!.fillRect(0, 0, canvas.width, canvas.height);

      ctx?.drawImage(img, 0, 0);

      // Blob으로 변환하여 내보내기
      canvas.toBlob((blob) => {
        if (!blob) return;
        const url = URL.createObjectURL(blob);
        const link = document.createElement("a");
        link.href = url;
        link.download = "sequence-diagram.png";
        link.click();
        URL.revokeObjectURL(url);
      }, "image/png");
    };

    // SVG 데이터를 base64로 인코딩하여 image source로 설정
    img.src =
      "data:image/svg+xml;base64," +
      btoa(unescape(encodeURIComponent(svgString)));
  };

  return (
    <div className="h-screen flex flex-col bg-slate-100">
      {/* Header */}
      <header className="bg-white border-b border-slate-200 px-4 sm:px-6 py-4">
        <div className="flex items-center justify-between">
          <h1 className="text-xl sm:text-2xl font-semibold text-slate-900">
            {t("title")}
          </h1>
          <div className="flex gap-2">
            <div className="hidden sm:flex gap-2">
              <Button
                variant="outline"
                size="sm"
                onClick={importDiagram}
              >
                <Upload className="h-4 w-4 mr-2" />
                {t("import")}
              </Button>
              <Button
                variant="outline"
                size="sm"
                onClick={exportDiagram}
              >
                <Download className="h-4 w-4 mr-2" />
                {t("export")}
              </Button>
              <Button
                variant="outline"
                size="sm"
                onClick={exportSVG}
              >
                <Image className="h-4 w-4 mr-2" />
                {t("exportSvg")}
              </Button>
              <Button
                variant="outline"
                size="sm"
                onClick={exportPNG}
              >
                <Image className="h-4 w-4 mr-2" />
                {t("exportPng")}
              </Button>
              <Button
                variant="outline"
                size="sm"
                onClick={clearAll}
              >
                <Trash2 className="h-4 w-4 mr-2" />
                {t("clear")}
              </Button>
            </div>

            {/* Mobile Menu Button */}
            <Select value={language} onValueChange={handleLanguageChange}>
              <SelectTrigger className="w-[100px] h-9">
                <SelectValue />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="ko">한국어</SelectItem>
                <SelectItem value="en">English</SelectItem>
              </SelectContent>
            </Select>

            <Sheet>
              <SheetTrigger asChild>
                <Button variant="outline" size="sm">
                  <Menu className="h-4 w-4" />
                </Button>
              </SheetTrigger>
              <SheetContent
                side="right"
                className="w-[90vw] sm:w-[400px] overflow-y-auto p-4"
              >
                <SheetTitle className="sr-only">
                  {t("menu")}
                </SheetTitle>
                <SheetDescription className="sr-only">
                  {t("menuDescription")}
                </SheetDescription>
                <div className="space-y-4">
                  {/* Mobile Actions */}
                  <div className="flex flex-col gap-2 sm:hidden pb-4 border-b">
                    <Button
                      variant="outline"
                      size="sm"
                      onClick={importDiagram}
                      className="w-full"
                    >
                      <Upload className="h-4 w-4 mr-2" />
                      {t("import")}
                    </Button>
                    <Button
                      variant="outline"
                      size="sm"
                      onClick={exportDiagram}
                      className="w-full"
                    >
                      <Download className="h-4 w-4 mr-2" />
                      {t("export")}
                    </Button>
                    <Button
                      variant="outline"
                      size="sm"
                      onClick={exportSVG}
                      className="w-full"
                    >
                      <Image className="h-4 w-4 mr-2" />
                      {t("exportSvg")}
                    </Button>
                    <Button
                      variant="outline"
                      size="sm"
                      onClick={exportPNG}
                      className="w-full"
                    >
                      <Image className="h-4 w-4 mr-2" />
                      {t("exportPng")}
                    </Button>
                    <Button
                      variant="outline"
                      size="sm"
                      onClick={clearAll}
                      className="w-full"
                    >
                      <Trash2 className="h-4 w-4 mr-2" />
                      {t("clear")}
                    </Button>
                  </div>

                  <ParticipantEditor
                    participants={participants}
                    onAdd={addParticipant}
                    onRemove={removeParticipant}
                    onUpdateIcon={updateParticipantIcon}
                    onUpdateName={updateParticipantName}
                    onReorder={handleReorderParticipants}
                  />

                  <MessageEditor
                    participants={participants}
                    messages={messages}
                    onAdd={addMessage}
                    onRemove={removeMessage}
                    onUpdate={updateMessage}
                    onMoveUp={moveMessageUp}
                    onMoveDown={moveMessageDown}
                    selectedMessage={selectedMessage}
                  />
                </div>
              </SheetContent>
            </Sheet>
          </div>
        </div>
      </header>

      {/* Main Content */}
      <div className="flex-1 flex overflow-hidden">
        {/* Diagram - Full width on mobile, with sidebar on desktop */}
        <div className="flex-1 overflow-auto">
          <SequenceDiagram
            participants={participants}
            messages={messages}
            onMessageClick={setSelectedMessage}
            ref={diagramRef}
            svgRef={svgRef}
          />
        </div>

        {/* Desktop Right Panel - Hidden on mobile */}
        <div className="hidden lg:block w-80 bg-white border-l border-slate-200 overflow-y-auto p-4 space-y-4">
          <ParticipantEditor
            participants={participants}
            onAdd={addParticipant}
            onRemove={removeParticipant}
            onUpdateIcon={updateParticipantIcon}
            onUpdateName={updateParticipantName}
            onReorder={handleReorderParticipants}
          />

          <MessageEditor
            participants={participants}
            messages={messages}
            onAdd={addMessage}
            onRemove={removeMessage}
            onUpdate={updateMessage}
            onMoveUp={moveMessageUp}
            onMoveDown={moveMessageDown}
            selectedMessage={selectedMessage}
          />
        </div>
      </div>
    </div>
  );
}